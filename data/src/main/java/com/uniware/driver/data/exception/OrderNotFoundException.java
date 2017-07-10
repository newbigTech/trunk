package com.uniware.driver.data.exception;

/**
 * Exception throw by the application when a Order search can't return a valid result.
 */
public class OrderNotFoundException extends Exception {

  public OrderNotFoundException() {
    super();
  }

  public OrderNotFoundException(final String message) {
    super(message);
  }

  public OrderNotFoundException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public OrderNotFoundException(final Throwable cause) {
    super(cause);
  }
}
