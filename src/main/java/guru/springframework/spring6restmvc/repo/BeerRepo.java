package guru.springframework.spring6restmvc.repo;

import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.model.BeerStyle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerRepo extends JpaRepository<Beer, UUID> {
    List<Beer> findAllByBeerStyle(BeerStyle beerStyle);

    List<Beer> findAllByBeerNameIsLikeIgnoreCaseAndBeerStyle(String beerName, BeerStyle beerStyle);

    Optional<Beer> findByUpc(String upc);

    List<Beer> findByPriceLessThan(BigDecimal price);

    List<Beer> findByPriceBetween(BigDecimal min, BigDecimal max);

    List<Beer> findByQuantityOnHandGreaterThan(Integer quantityOnHand);

    Page<Beer> findAllByBeerStyle(BeerStyle beerStyle, Pageable pageable);
    Page<Beer> findAllByBeerNameIsLikeIgnoreCase(String beerName, Pageable pageable);
    Page<Beer> findAllByBeerNameIsLikeIgnoreCaseAndBeerStyle(String beerName, BeerStyle beerStyle, Pageable pageable);

    @Query("SELECT b FROM Beer b WHERE b.beerName LIKE %:name% AND b.beerStyle = :style")
    List<Beer> searchByNameAndStyle(@Param("name") String beerName, @Param("style") BeerStyle beerStyle);

    @Query(value = "SELECT * FROM beer WHERE price < :price", nativeQuery = true)
    List<Beer> findCheaperThan(@Param("price") BigDecimal price);

    Long countByBeerStyle(BeerStyle beerStyle);

    boolean existsByUpc(String upc);

    void deleteByUpc(String upc);
}