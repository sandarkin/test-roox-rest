package ru.sandarkin.roox.controller;

import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
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

  /**
   * Shortcut for findOne for current logged user.
   */
  @RequestMapping(value = "/customers/@me", method = RequestMethod.GET)
  public ResponseEntity<Resource<?>> getMe(Principal principal,
                                           PersistentEntityResourceAssembler assembler) {
    Customer customer = customerRepository.findOne(((JwtAuthToken)principal).getId());
    PersistentEntityResource resource = assembler.toFullResource(customer);
    return new ResponseEntity<>(resource, HttpStatus.OK);

  }

}
