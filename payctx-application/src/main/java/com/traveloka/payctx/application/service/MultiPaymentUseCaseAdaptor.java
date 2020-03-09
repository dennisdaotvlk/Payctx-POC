package com.traveloka.payctx.application.service;

import com.traveloka.payctx.application.request.MultiPaymentConfigPayload;
import com.traveloka.payctx.application.request.MultiPaymentConfigRequest;

import java.util.List;

/**
 * @author sandeepandey
 */
public interface MultiPaymentUseCaseAdaptor {

  void handleSaveConfigCommand(MultiPaymentConfigRequest request);
  List<MultiPaymentConfigPayload> getMultiplePaymentConfig();

}
