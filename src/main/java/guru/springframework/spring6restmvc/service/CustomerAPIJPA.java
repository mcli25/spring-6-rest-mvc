package guru.springframework.spring6restmvc.service;

import guru.springframework.spring6restmvc.mappers.CustomerMapper;
import guru.springframework.spring6restmvc.model.CustomerDTO;
import guru.springframework.spring6restmvc.repo.CustomerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
@Primary
@RequiredArgsConstructor
public class CustomerAPIJPA implements CustomerAPI{
    private CustomerMapper customerMapper;
    private CustomerRepo customerRepo;
    @Override
    public CustomerDTO getCustomerById(UUID id) {
        return null;
    }

    @Override
    public List<CustomerDTO> listCustomers() {
        return null;
    }

    @Override
    public CustomerDTO saveNewCustomer(CustomerDTO customerDTO) {
        return null;
    }

    @Override
    public CustomerDTO updateCustomerById(UUID id, CustomerDTO customerDTO) {
        return null;
    }

    @Override
    public CustomerDTO deleteCustomerById(UUID id) {
        return null;
    }
}
