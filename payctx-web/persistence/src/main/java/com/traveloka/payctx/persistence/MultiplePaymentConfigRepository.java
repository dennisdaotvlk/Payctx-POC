package com.traveloka.payctx.persistence;

/**
 * @author sandeepandey
 */
public interface MultiplePaymentConfigRepository {

  PersistableMultiPaymentConfig readConfig();

  void save(PersistableMultiPaymentConfig persistableMultiplePaymentConfig);

  void cleanup();


}
