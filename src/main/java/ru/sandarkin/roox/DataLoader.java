package ru.sandarkin.roox;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

import ru.sandarkin.roox.model.Customer;
import ru.sandarkin.roox.repository.CustomerRepository;
import ru.sandarkin.roox.repository.PartnerMappingRepository;

@Component
public class DataLoader implements ApplicationRunner {

  private final CustomerRepository customerRepository;
  private final PartnerMappingRepository partnerMappingRepository;

  public DataLoader(CustomerRepository customerRepository, PartnerMappingRepository partnerMappingRepository) {
    this.customerRepository = customerRepository;
    this.partnerMappingRepository = partnerMappingRepository;
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {

    Customer kevin = new Customer(UUID.fromString("9ba5c7bb-8040-4450-a2fe-e677eb82fc63"));
    kevin.setFullName("Kevin McCallister");
    kevin.setBalance(100);
    kevin.setActive(true);
    kevin.setUsername("kevin");
    kevin.setPassword("kSp2zmcs");
    customerRepository.save(kevin);

    Customer fuller = new Customer(UUID.fromString("f5593d22-1fc7-4622-9257-d15b261f15a9"));
    fuller.setFullName("Fuller McCallister");
    fuller.setBalance(50);
    fuller.setActive(true);
    fuller.setUsername("fuller");
    fuller.setPassword("ioyerw87y");
    customerRepository.save(fuller);

    Customer marley = new Customer(UUID.fromString("25d6bcec-2ee4-4565-9797-57f12c89a973"));
    marley.setFullName("Old Man Marley");
    marley.setBalance(500);
    marley.setActive(true);
    marley.setUsername("marley");
    marley.setPassword("ioyerw87y");
    customerRepository.save(marley);

  }

}
