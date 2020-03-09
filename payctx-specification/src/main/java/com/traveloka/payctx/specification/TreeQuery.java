package com.traveloka.payctx.specification;


import com.traveloka.payctx.common.PaymentMethod;
import com.traveloka.payctx.common.model.TreeQueryResult;

/**
 * @author sandeepandey
 */
public interface TreeQuery {

  default TreeQueryResult nextMethodsByPrimaryMethod(PaymentMethod primaryMethodName) {
    return nextMethodsByPrimaryMethod(primaryMethodName, 1);
  }

  TreeQueryResult nextMethodsByPrimaryMethod(PaymentMethod primaryMethodName, int uptoLevel);

  Boolean checkIfCombinationPresent(PaymentMethod primary,PaymentMethod secondary);

  public void upgradeTree(PaymentMethod forNode);

}
