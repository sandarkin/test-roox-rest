package ru.sandarkin.roox.model;

import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="CUSTOMER")
public class Customer {

  @Id
  @Column(columnDefinition="BINARY(16)")
  @Setter(AccessLevel.NONE)
  private UUID id;

  @Column(nullable = false)
  private String fullName;
  private Integer balance;
  private boolean active;
  private String username;
  private String password;

  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
  private Set<PartnerMapping> partnerMappings;

  public Customer() {
    this.id = UUID.randomUUID();
  }

  public Customer(UUID id) {
    this.id = id;
  }

}
