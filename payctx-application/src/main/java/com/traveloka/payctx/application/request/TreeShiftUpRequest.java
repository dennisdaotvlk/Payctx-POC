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
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TreeShiftUpRequest {
  private PaymentMethod primary;
}
