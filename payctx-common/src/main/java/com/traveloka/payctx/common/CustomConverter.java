package com.traveloka.payctx.common;


import java.io.Serializable;
import java.util.function.Function;

/**
 * @param <T> - Source object
 * @param <U> - Target object
 * @author sandeepandey
 */
public interface CustomConverter<T, U> extends Serializable {

  U from(T sourceObject);

  T to(U targetObject);

  Class<T> fromType();

  Class<U> toType();


  static <T, U> CustomConverter<T, U> of(
      Class<T> fromType,
      Class<U> toType,
      Function<? super T, ? extends U> from,
      Function<? super U, ? extends T> to
  ) {
    return new CustomConverter<>() {
      @Override
      public U from(T sourceObject) {
        return from.apply(sourceObject);
      }

      @Override
      public T to(U targetObject) {
        return to.apply(targetObject);
      }

      @Override
      public Class<T> fromType() {
        return fromType;
      }

      @Override
      public Class<U> toType() {
        return toType;
      }
    };
  }


}
