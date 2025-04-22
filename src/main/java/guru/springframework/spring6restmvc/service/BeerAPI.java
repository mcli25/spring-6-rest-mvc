package guru.springframework.spring6restmvc.service;

import guru.springframework.spring6restmvc.model.BeerDTO;

import java.util.List;
import java.util.UUID;

public interface BeerAPI {
    BeerDTO getBeerById(UUID id);
    List<BeerDTO> listBeers();
    BeerDTO saveNewBeer(BeerDTO beerDTO);
    BeerDTO updateBeerById(UUID id, BeerDTO beerDTO);
    BeerDTO deleteBeerById(UUID id);
}
