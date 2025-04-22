package guru.springframework.spring6restmvc.service;

import guru.springframework.spring6restmvc.model.CustomerDTO;

import java.util.List;
import java.util.UUID;

public interface CustomerAPI {
    CustomerDTO getCustomerById(UUID id);
    List<CustomerDTO> listCustomers();
    CustomerDTO saveNewCustomer(CustomerDTO customerDTO);
    CustomerDTO updateCustomerById(UUID id, CustomerDTO customerDTO);
    CustomerDTO deleteCustomerById(UUID id);
}
