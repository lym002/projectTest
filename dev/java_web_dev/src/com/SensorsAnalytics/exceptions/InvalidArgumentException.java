package com.SensorsAnalytics.exceptions;

/**
 * 非法的DistinctID
 */
public class InvalidArgumentException extends Exception {

  public InvalidArgumentException(String message) {
    super(message);
  }

  public InvalidArgumentException(Throwable error) {
    super(error);
  }

}
