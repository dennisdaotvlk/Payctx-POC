package com.traveloka.payctx.application.request;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author sandeepandey
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class MultiPaymentConfigRequest {

  public enum ConfigState {
    ACTIVE,
    NOT_ACTIVE
  }

  private String versionId;
  private String configName;
  private ConfigState configState;
  private List<MultiPaymentConfigPayload> payload;
}
