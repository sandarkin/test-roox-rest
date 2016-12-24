package ru.sandarkin.roox;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

import ru.sandarkin.roox.model.Customer;
import ru.sandarkin.roox.model.PartnerMapping;
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

    PartnerMapping kevinMapping1 = new PartnerMapping(UUID.fromString("356afa54-4434-4399-8b3d-c9fe82df530e"));
    kevinMapping1.setPartnerId("973065359403599");
    kevinMapping1.setFullName("Kevin on FB");
    kevinMapping1.setAccountId("100001006414253");
    kevinMapping1.setCustomer(kevin);
    kevinMapping1.setAvatar("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAvNJREFUeNp8k11MUmEYx/9wQErIWaEoKjRtgqI1m9kszaGtqZu65SzXpJu2Ppbd1LzVzS7r0nnfRbKpiV554Y06q6m4RV8iRsgMooGBggJ6gJ73ZMtqdbYfh73n+f+fj/d9RW1tbRCJRBCLxcKbyCJKAKiIDPx4toivqVRqmfATSCaTYG8Jfj0yokqr1V7o6Oi4XlhYWM7zvPBBIpHA6XS+HRkZGXK73S9oaYGIs2+cXq9nWZn4cktLS5fJZHrI22wq3/AwvgwOIjA2hrDTCYVcrqq/dvUSVco5HA4mdhMJTqfTsVJqWltbu4xG4w1XXx+kc3PQUlZtbi7ylEootrawOTsLn3UJ5aau0xzH8WTiZybiRCKh0mg0tQ0N9SZnXy9yKDhHpcL29jYCgYDAzs4OVLSm3NjAWn8/6urqTHl5ebVMywwM7e1XOj9PTSHD44WIBuP1ehEMBn/D5/NBxAblWoN/ZhrNzU2dTCuhH3VBQYHB8WwIJxQKeDweYXB7u7tC5h2qZJf+/xyoOj8fWLQi985tA9Myg8xwOIKozQY+KwvVMzNCBaFQCNQr5HI5FGSclpYmGExXVCBFsdFoDEwrIWfWBnja1+jmJux2u5BNKpX+JRa2jaqJUaygIS0zCHOcBFJDGWLWRbiNRvzvSSczFguImUGYZfeurn60KyqrEIzFoJHJoGa9/oMotZV+5ixcrjU70zKDN2az2aKsqUO87BQ2KeAIHWsFBf9JmNpKUqKj1TWYmLBYmJbLzs6OBIPfpIDo8MV7D0o/vXsD/7obclauUCgJiVVi+9x5lDx6gvHx0efz8y8tdABfc/sXw7O8/F5MsxE39vTqcbIYbk6KJc86HBIp+PpGqG/eRfGt+zCbn46Pjg5ZotHoJJ0PXrSf5JBMJjtGEzfqdKVV3d09TZWV1UUHh2e1vnIODDyeXFn5sBCJRKbj8fgGLceYAUdkEsdZq7R9mXT7iujSZB28zlSln6bu3NvbC7FuiAAR+i7AAKjye47FnCxuAAAAAElFTkSuQmCC".getBytes());
    partnerMappingRepository.saveAndFlush(kevinMapping1);

    PartnerMapping kevinMapping2 = new PartnerMapping(UUID.fromString("f7e1fa4c-c98c-11e6-9d9d-cec0c932ce01"));
    kevinMapping2.setPartnerId("555345673456700");
    kevinMapping2.setFullName("Kevin on Twitter");
    kevinMapping2.setAccountId("69532555");
    kevinMapping2.setCustomer(kevin);
    partnerMappingRepository.saveAndFlush(kevinMapping2);

    PartnerMapping fullerMapping1 = new PartnerMapping();
    fullerMapping1.setPartnerId("555345673456700");
    fullerMapping1.setFullName("Fuller on Twitter");
    fullerMapping1.setAccountId("69532555");
    fullerMapping1.setCustomer(fuller);
    partnerMappingRepository.saveAndFlush(fullerMapping1);

  }

}
