package guru.springframework.spring6restmvc.service;

import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.mappers.BeerMapper;
import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.model.BeerStyle;
import guru.springframework.spring6restmvc.repo.BeerRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Primary
@RequiredArgsConstructor
@Slf4j
public class BeerAPIJPA implements BeerAPI {
    private final BeerRepo beerRepo;
    private final BeerMapper beerMapper;

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_PAGE_SIZE = 25;

    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        log.debug("Getting beer by id: {}", id);
        return beerRepo.findById(id)
                .map(beerMapper::beerToBeerDTO);
    }

    @Override
    public Page<BeerDTO> listBeers(String beerName, BeerStyle beerStyle,
                                   Boolean showInventory, Integer pageNumber,
                                   Integer pageSize) {
        log.debug("Listing beers with filters - name: {}, style: {}", beerName, beerStyle);

        // Set default pagination if not provided
        int page = pageNumber == null ? DEFAULT_PAGE : pageNumber;
        int size = pageSize == null ? DEFAULT_PAGE_SIZE : pageSize;
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
        Page<Beer> beerPage;

        if (StringUtils.hasText(beerName) && beerStyle != null) {
            beerPage = beerRepo.findAllByBeerNameIsLikeIgnoreCaseAndBeerStyle("%" + beerName + "%", beerStyle, pageRequest);
        } else if (StringUtils.hasText(beerName)) {
            beerPage = beerRepo.findAllByBeerNameIsLikeIgnoreCase("%" + beerName + "%", pageRequest);
        } else if (beerStyle != null) {
            beerPage = beerRepo.findAllByBeerStyle(beerStyle, pageRequest);
        } else {
            beerPage = beerRepo.findAll(pageRequest);
        }

        return beerPage.map(beerMapper::beerToBeerDTO);
    }

    private PageRequest buildPageRequest(Integer pageNumber, Integer pageSize) {
        int page = pageNumber == null ? DEFAULT_PAGE : pageNumber;
        int size = pageSize == null ? DEFAULT_PAGE_SIZE : pageSize;

        return PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "beerName"));
    }

    @Override
    @Transactional
    public BeerDTO saveNewBeer(BeerDTO beerDTO) {
        log.debug("Saving new beer: {}", beerDTO.getBeerName());

        Beer beer = beerMapper.beerDTOToBeer(beerDTO);

        // Set createdDate and updateDate for new beers
        beer.setCreatedDate(LocalDateTime.now());
        beer.setUpdateDate(LocalDateTime.now());
        beer.setVersion(0);

        Beer savedBeer = beerRepo.save(beer);

        return beerMapper.beerToBeerDTO(savedBeer);
    }

    @Override
    @Transactional
    public BeerDTO updateBeerById(UUID beerId, BeerDTO beerDTO) {
        log.debug("Updating beer with id: {}", beerId);

        AtomicReference<BeerDTO> atomicReference = new AtomicReference<>();

        beerRepo.findById(beerId).ifPresentOrElse(foundBeer -> {
            foundBeer.setBeerName(beerDTO.getBeerName());
            foundBeer.setBeerStyle(beerDTO.getBeerStyle());
            foundBeer.setUpc(beerDTO.getUpc());
            foundBeer.setPrice(beerDTO.getPrice());
            foundBeer.setQuantityOnHand(beerDTO.getQuantityOnHand());
            foundBeer.setUpdateDate(LocalDateTime.now());

            atomicReference.set(beerMapper.beerToBeerDTO(beerRepo.save(foundBeer)));
        }, () -> {
            log.warn("Beer not found with id: {}", beerId);
        });

        return atomicReference.get();
    }

    @Override
    @Transactional
    public BeerDTO deleteBeerById(UUID beerId) {
        log.debug("Deleting beer with id: {}", beerId);

        AtomicReference<BeerDTO> atomicReference = new AtomicReference<>();

        beerRepo.findById(beerId).ifPresentOrElse(beer -> {
            beerRepo.delete(beer);
            atomicReference.set(beerMapper.beerToBeerDTO(beer));
        }, () -> {
            log.warn("Beer not found with id: {}", beerId);
        });

        return atomicReference.get();
    }
}