package com.traveloka.payctx.application.exceptions;

import com.traveloka.payctx.common.exception.PayctxBaseException;

/**
 * @author sandeepandey
 */
public class PayctxApplicationException extends PayctxBaseException {
  public PayctxApplicationException() {
    super();
  }

  public PayctxApplicationException(String message) {
    super(message);
  }

  public PayctxApplicationException(String message, Throwable cause) {
    super(message, cause);
  }
}
