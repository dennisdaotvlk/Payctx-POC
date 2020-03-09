package com.traveloka.payctx.common.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author sandeepandey
 */

public class GenericBuilder<T> {

  private final Supplier<T> instanceControlledSupplier;

  private List<Consumer<T>> instanceModifiers = new ArrayList<>();

  public GenericBuilder(Supplier<T> instanceControlledSupplier) {
    this.instanceControlledSupplier = instanceControlledSupplier;
  }

  public static <T> GenericBuilder<T> of(Supplier<T> instanceControlledSupplier) {
    return new GenericBuilder<>(instanceControlledSupplier);
  }

  public <U> GenericBuilder<T> with(BiConsumer<T, U> consumer, U value) {
    Consumer<T> c = instance -> consumer.accept(instance, value);
    instanceModifiers.add(c);
    return this;
  }

  public T build() {
    T value = instanceControlledSupplier.get();
    instanceModifiers.forEach(modifier -> modifier.accept(value));
    instanceModifiers.clear();
    return value;
  }
}