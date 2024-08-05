package org.example.ecommercefashion.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ExceptionHandle extends RuntimeException {

  HttpStatus status;

  String messageCode;

  public ExceptionHandle(HttpStatus status, BaseErrorMessage msg) {
    super(msg.val());
    this.status = status;
    this.messageCode = msg.toString();
  }

  public ExceptionHandle(HttpStatus status, BaseErrorMessage msg, String data) {
    super(msg.val() + "(" + data + ")");
    this.status = status;
    this.messageCode = msg.toString();
  }

  public ExceptionHandle(HttpStatus status, String msg) {
    super(msg);
    this.status = status;
  }
}
