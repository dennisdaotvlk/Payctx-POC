package com.traveloka.payctx.common.exception;

/**
 * @author sandeepandey
 */
public class PayctxBaseException extends RuntimeException {
  public PayctxBaseException() {
    super();
  }

  public PayctxBaseException(String message) {
    super(message);
  }

  public PayctxBaseException(String message, Throwable cause) {
    super(message, cause);
  }

  public PayctxBaseException(Throwable cause) {
    super(cause);
  }
}
