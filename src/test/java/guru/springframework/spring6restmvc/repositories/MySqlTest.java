package guru.springframework.spring6restmvc.repositories;

import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.model.BeerStyle;
import guru.springframework.spring6restmvc.repo.BeerRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
public class MySqlTest {

    @Container
    @ServiceConnection
    static final MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.4.5");
    @Autowired
    private DataSource dataSource;

    @Autowired
    private BeerRepo beerRepo;

    @Test
    void testContainerIsRunning() {
        assertThat(mysql.isRunning()).isTrue();
    }

    @Test
    void testDataSourceConnection() throws Exception {
        try (var connection = dataSource.getConnection()) {
            assertThat(connection.isValid(1000)).isTrue();
        }
    }

    @Test
    void testSaveAndRetrieveBeer() {
        Beer beer = Beer.builder()
                .beerName("Test Beer")
                .beerStyle(BeerStyle.IPA)
                .upc("123456789")
                .price(new BigDecimal("9.99"))
                .quantityOnHand(100)
                .build();

        Beer savedBeer = beerRepo.save(beer);

        assertThat(savedBeer.getId()).isNotNull();

        Beer foundBeer = beerRepo.findById(savedBeer.getId()).orElseThrow();
        assertThat(foundBeer.getBeerName()).isEqualTo("Test Beer");
    }

    @Test
    void testFindByBeerStyle() {
        Beer beer = Beer.builder()
                .beerName("Pale Ale")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("987654321")
                .price(new BigDecimal("8.99"))
                .build();

        beerRepo.save(beer);

        List<Beer> foundBeers = beerRepo.findAllByBeerStyle(BeerStyle.PALE_ALE);
        assertThat(foundBeers).isNotEmpty();
        assertThat(foundBeers.get(0).getBeerStyle()).isEqualTo(BeerStyle.PALE_ALE);
    }

    @Test
    void testUpdateAndDeleteBeer() {
        Beer beer = beerRepo.save(Beer.builder()
                .beerName("To Update")
                .beerStyle(BeerStyle.LAGER)
                .upc("111222333")
                .price(new BigDecimal("5.99"))
                .build());

        beer.setBeerName("Updated Name");
        Beer updatedBeer = beerRepo.save(beer);
        assertThat(updatedBeer.getBeerName()).isEqualTo("Updated Name");

        beerRepo.deleteById(updatedBeer.getId());
        assertThat(beerRepo.findById(updatedBeer.getId())).isEmpty();
    }
}