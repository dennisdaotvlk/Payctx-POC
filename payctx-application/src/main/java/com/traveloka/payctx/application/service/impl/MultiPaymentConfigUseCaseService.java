package com.traveloka.payctx.application.service.impl;

import com.traveloka.payctx.application.request.MultiPaymentConfigRequest;
import com.traveloka.payctx.application.service.MultiPaymentConfigUseCase;
import com.traveloka.payctx.application.service.MultiPaymentUseCaseAdaptor;
import com.traveloka.payctx.specification.TreeManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author sandeepandey
 */
@Service
@Slf4j
public class MultiPaymentConfigUseCaseService implements MultiPaymentConfigUseCase {

  private final MultiPaymentUseCaseAdaptor adaptor;

  private final SimpleTreeManager treeManager;

  @Autowired
  public MultiPaymentConfigUseCaseService(MultiPaymentUseCaseAdaptor adaptor, SimpleTreeManager treeManager) {

    this.adaptor = adaptor;
    this.treeManager = treeManager;
  }

  @Override
  public void handleSaveUseCase(MultiPaymentConfigRequest request) {
    adaptor.handleSaveConfigCommand(request);
    treeManager.organizeTree(request);
  }
}
