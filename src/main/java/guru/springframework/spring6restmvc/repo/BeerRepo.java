package guru.springframework.spring6restmvc.repo;

import guru.springframework.spring6restmvc.entities.Beer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BeerRepo extends JpaRepository<Beer, UUID> {
}
