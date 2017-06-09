package com.uniware.driver.mvp.exception;

import android.content.Context;

import com.uniware.driver.data.exception.NetworkConnectionException;
import com.uniware.driver.data.exception.OrderNotFoundException;
import com.uniware.driver.R;

/**
 * Factory used to create error messages from an Exception as a condition.
 */
public class ErrorMessageFactory {

  private ErrorMessageFactory() {
    //empty
  }

  /**
   * Creates a String representing an error message.
   *
   * @param context Context needed to retrieve string resources.
   * @param exception An exception used as a condition to retrieve the correct error message.
   * @return {@link String} an error message.
   */
  public static String create(Context context, Exception exception) {
    String message = exception.getMessage() + " " + exception.getCause().toString();

    if (exception instanceof NetworkConnectionException) {
      message = context.getString(R.string.exception_message_no_connection);
    } else if (exception instanceof OrderNotFoundException) {
      message = context.getString(R.string.exception_message_user_not_found);
    }

    return message;
  }
}
