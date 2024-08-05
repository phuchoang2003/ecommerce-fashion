package org.example.ecommercefashion.exceptions;

public enum ErrorMessage implements BaseErrorMessage {
  SUCCESS("Success"),
  FALSE("False");

  public String val;

  private ErrorMessage(String label) {
    val = label;
  }

  @Override
  public String val() {
    return val;
  }
}
