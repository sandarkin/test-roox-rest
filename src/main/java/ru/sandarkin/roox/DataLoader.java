package ru.sandarkin.roox;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

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

    Customer kevinMcCallister = new Customer();
    kevinMcCallister.setFullName("Kevin McCallister");
    kevinMcCallister.setBalance(100);
    kevinMcCallister.setActive(true);
    kevinMcCallister.setUsername("k.callister");
    kevinMcCallister.setPassword("kSp2zmcs");

    customerRepository.save(kevinMcCallister);

  }

}
