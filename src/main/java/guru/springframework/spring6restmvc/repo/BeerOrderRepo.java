package guru.springframework.spring6restmvc.repo;

import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.entities.BeerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BeerOrderRepo extends JpaRepository<BeerOrder, UUID> {
}
