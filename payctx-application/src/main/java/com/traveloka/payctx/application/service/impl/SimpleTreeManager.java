package com.traveloka.payctx.application.service.impl;

import com.traveloka.payctx.application.request.MultiPaymentConfigPayload;
import com.traveloka.payctx.application.request.MultiPaymentConfigRequest;
import com.traveloka.payctx.application.service.MultiPaymentUseCaseAdaptor;
import com.traveloka.payctx.specification.TreeManager;
import com.traveloka.payctx.specification.TreeOperator;
import com.traveloka.payctx.specification.TreeQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Semaphore;
import javax.annotation.PostConstruct;

/**
 * @author sandeepandey
 */
@Service
@Slf4j
public class SimpleTreeManager implements TreeManager<MultiPaymentConfigRequest> {

  private Semaphore locker;
  private final TreeOperator<List<MultiPaymentConfigPayload>> treeOperator;

  private final MultiPaymentUseCaseAdaptor multiPaymentUseCaseAdaptor;

  private final SimpleTreeQueryInterface queryInterface;

  @Autowired
  public SimpleTreeManager(SimpleTreeOperator treeOperator, MultiPaymentUseCaseAdaptor multiPaymentUseCaseAdaptor, SimpleTreeQueryInterface queryInterface) {
    this.treeOperator = treeOperator;
    this.multiPaymentUseCaseAdaptor = multiPaymentUseCaseAdaptor;
    this.queryInterface = queryInterface;
  }

  @PostConstruct
  private void init() {
    locker = new Semaphore(1);
    treeOperator.preInit();
  }

  @Override
  public void organizeTree(MultiPaymentConfigRequest treeInput) {
    try {
      locker.acquire();
      organizeTreeInternal(treeInput.getPayload());
    } catch (Exception ex) {
      log.error("Unable to initialize Tree :::{}", ex.getLocalizedMessage());
    } finally {
      locker.release();
    }
  }

  private void organizeTreeInternal(List<MultiPaymentConfigPayload> payloads) {
    treeOperator.initState(payloads);
  }

  @Override
  public void warmUp() {
    log.info("Reading multiple payment config from persistence store ...");
    List<MultiPaymentConfigPayload> configFromDB
        = multiPaymentUseCaseAdaptor.getMultiplePaymentConfig();
    organizeTreeInternal(configFromDB);
  }




}
