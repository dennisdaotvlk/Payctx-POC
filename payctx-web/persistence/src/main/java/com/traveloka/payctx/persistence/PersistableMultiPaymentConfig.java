package com.traveloka.payctx.persistence;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author sandeepandey
 */
@Document(collection = "multiplePaymentConfigStore")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersistableMultiPaymentConfig {
  @Id
  private String id;
  private String status;
  private String versionId;
  private String name;
  private List<PersistableMultiPaymentConfigEntry> configs;
  
}
