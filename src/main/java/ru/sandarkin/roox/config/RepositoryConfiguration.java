package ru.sandarkin.roox.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

import ru.sandarkin.roox.model.Customer;
import ru.sandarkin.roox.model.PartnerMapping;

@Configuration
public class RepositoryConfiguration extends RepositoryRestConfigurerAdapter {

  @Override
  public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
    config.exposeIdsFor(Customer.class, PartnerMapping.class);
  }

}
