package guru.springframework.spring6restmvc.service;

import guru.springframework.spring6restmvc.model.BeerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class BeerService implements BeerAPI{

    @Override
    public BeerDTO getBeerById(UUID id) {
        return BeerDTO.builder()
                .id(id)
                .id(id)
                .build();
    }

    @Override
    public List<BeerDTO> listBeers() {
        return null;
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beerDTO) {
        return null;
    }

    @Override
    public BeerDTO updateBeerById(UUID id, BeerDTO beerDTO) {
        return null;
    }

    @Override
    public BeerDTO deleteBeerById(UUID id) {
        return null;
    }
}
