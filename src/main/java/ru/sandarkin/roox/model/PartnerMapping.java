package ru.sandarkin.roox.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="PARTNERMAPPING")
public class PartnerMapping {

  @Id
  @Column(columnDefinition="BINARY(16)")
  @Setter(AccessLevel.NONE)
  private UUID id;

  private String partnerId;
  private String accountId;
  private String fullName;
  private byte[] avatar;

}
