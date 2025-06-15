package guru.springframework.spring6restmvc.repo;

import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.model.BeerStyle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@RepositoryRestResource(path = "beer", collectionResourceRel = "beer")
public interface BeerRepo extends JpaRepository<Beer, UUID> {

    Page<Beer> findByBeerStyle(@Param("beerStyle") BeerStyle beerStyle, Pageable pageable);

    Optional<Beer> findByUpc(@Param("upc") String upc);

    Page<Beer> findByPriceLessThan(@Param("price") BigDecimal price, Pageable pageable);

    Page<Beer> findByBeerNameContainingIgnoreCase(@Param("name") String name, Pageable pageable);
    Page<Beer> findAllByBeerNameIsLikeIgnoreCaseAndBeerStyle(
            @Param("beerName") String beerName,
            @Param("beerStyle") BeerStyle beerStyle,
            Pageable pageable);

    Page<Beer> findAllByBeerNameIsLikeIgnoreCase(
            @Param("beerName") String beerName,
            Pageable pageable);

    Page<Beer> findAllByBeerStyle(
            @Param("beerStyle") BeerStyle beerStyle,
            Pageable pageable);

    @RestResource(path = "by-style-list")
    List<Beer> findAllByBeerStyle(@Param("beerStyle") BeerStyle beerStyle);

}