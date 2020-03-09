package com.traveloka.payctx.specification;

/**
 * @author sandeepandey
 */
public interface TreeManager<T> {

  void organizeTree(T treeInput);

  void warmUp();

}
