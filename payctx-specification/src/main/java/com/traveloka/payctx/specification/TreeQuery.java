package com.traveloka.payctx.specification;


import com.traveloka.payctx.common.PaymentMethod;
import com.traveloka.payctx.common.model.TreeQueryResult;

import java.util.function.Predicate;

/**
 * @author sandeepandey
 */
public interface TreeQuery {
  default TreeQueryResult nextMethodsByPrimaryMethod(PaymentMethod primaryMethodName) {
    return nextMethodsByPrimaryMethod(primaryMethodName, 1);
  }

  default TreeQueryResult nextMethodsByPrimaryMethod(PaymentMethod primaryMethodName, int uptoLevel) {
    return nextMethodsByPrimaryMethod(primaryMethodName, uptoLevel, s -> true);
  }

  TreeQueryResult nextMethodsByPrimaryMethod(PaymentMethod primaryMethodName, int uptoLevel, Predicate<String> predicate);

  Boolean checkIfCombinationPresent(PaymentMethod primary,PaymentMethod secondary);

  public void upgradeTree(PaymentMethod forNode);

}
