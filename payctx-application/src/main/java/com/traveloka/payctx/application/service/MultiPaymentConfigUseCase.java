package com.traveloka.payctx.application.service;

import com.traveloka.payctx.application.request.MultiPaymentConfigRequest;

/**
 * @author sandeepandey
 */
public interface MultiPaymentConfigUseCase {

  void handleSaveUseCase(MultiPaymentConfigRequest request);

}
