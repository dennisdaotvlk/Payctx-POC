package com.traveloka.payctx.application.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author sandeepandey
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MultiPaymentConfigPayload {

  private String primaryMethod;
  private List<String> nextMethods;
}
