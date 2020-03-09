package com.traveloka.payctx.specification;

/**
 *
 * @author sandeepandey
 */
public interface TreeOperator<T> {

  void preInit();

  void initState(T input);

  default void levelUp(String root) {
    levelUp(root, 1);
  }

  void levelUp(String root, int byLevel);
}
