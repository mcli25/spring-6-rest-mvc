package guru.springframework.spring6restmvc.service;

import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.mappers.BeerMapper;
import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.repo.BeerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class BeerAPIJPA implements BeerAPI{
    private final BeerRepo beerRepo;
    private final BeerMapper beerMapper;
    @Override
    public BeerDTO getBeerById(UUID id) {
        return beerRepo.findById(id)
                .map(beerMapper::beerToBeerDTO)
                .orElse(null);
    }

    @Override
    public List<BeerDTO> listBeers() {
        return beerRepo.findAll()
                .stream()
                .map(beerMapper::beerToBeerDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beerDTO) {
        Beer beer = beerMapper.beerDTOToBeer(beerDTO);
        Beer savedBeer = beerRepo.save(beer);
        return beerMapper.beerToBeerDTO(savedBeer);
    }

    @Override
    public BeerDTO updateBeerById(UUID id, BeerDTO beerDTO) {
        return beerRepo.findById(id)
                .map(existingBeer -> {
                    existingBeer.setBeerName(beerDTO.getBeerName());
                    existingBeer.setBeerStyle(beerDTO.getBeerStyle());
                    existingBeer.setUpc(beerDTO.getUpc());
                    existingBeer.setPrice(beerDTO.getPrice());
                    existingBeer.setQuantityOnHand(beerDTO.getQuantityOnHand());
                    return beerMapper.beerToBeerDTO(beerRepo.save(existingBeer));
                })
                .orElse(null);
    }

    @Override
    public BeerDTO deleteBeerById(UUID id) {
        return beerRepo.findById(id)
                .map(beer -> {
                    beerRepo.delete(beer);
                    return beerMapper.beerToBeerDTO(beer);
                })
                .orElse(null);
    }
}
