package ru.sandarkin.roox.controller;

import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

import ru.sandarkin.roox.model.Customer;
import ru.sandarkin.roox.repository.CustomerRepository;
import ru.sandarkin.roox.security.JwtAuthToken;

@RepositoryRestController
public class CustomerController {

  private CustomerRepository customerRepository;

  public CustomerController(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @RequestMapping(value = "/customers/@me", method = RequestMethod.GET)
  public PersistentEntityResource getMe(Principal principal,
                                        PersistentEntityResourceAssembler assembler) {
    Customer customer = customerRepository.findOne(((JwtAuthToken)principal).getId());
    return assembler.toResource(customer);
  }

}
