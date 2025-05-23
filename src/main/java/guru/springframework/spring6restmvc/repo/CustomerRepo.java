package guru.springframework.spring6restmvc.repo;

import guru.springframework.spring6restmvc.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepo extends JpaRepository<Customer, UUID> {
}
