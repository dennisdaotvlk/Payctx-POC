package com.traveloka.payctx.application.request;

import com.traveloka.payctx.common.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author sandeepandey
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ListPaymentCombinationRequest {
  private PaymentMethod primary;
  private Integer uptoLevel;
}
