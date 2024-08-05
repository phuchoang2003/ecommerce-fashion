package org.example.ecommercefashion.exceptions;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalHandleException extends ResponseEntityExceptionHandler {

  @ExceptionHandler({Exception.class})
  public ResponseEntity<ExceptionResponse> globalExceptionHandler(
      Exception ex, HttpServletRequest request) {
    sendNotification(ex, request);
    if (ex instanceof ExceptionHandle) {
      ExceptionHandle exceptionHandle = (ExceptionHandle) ex;
      ExceptionResponse exceptionResponse1 =
          new ExceptionResponse(
              exceptionHandle.status,
              new Date(),
              exceptionHandle.getMessage(),
              exceptionHandle.messageCode,
              exceptionHandle.getMessage(),
              request.getServletPath());
      return new ResponseEntity(exceptionResponse1, exceptionHandle.status);
    }
    ExceptionResponse exceptionResponse =
        new ExceptionResponse(
            HttpStatus.INTERNAL_SERVER_ERROR,
            new Date(),
            "clxra.",
            "INTERNAL_SERVER_ERROR",
            ex.getMessage(),
            request.getServletPath());
    return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {
    ValidDetails validDetails = new ValidDetails();
    Map<String, String> message = new HashMap<>();
    if (ex instanceof MethodArgumentNotValidException) {
      List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
      for (FieldError fieldError : fieldErrors)
        message.put(fieldError.getField(), fieldError.getDefaultMessage());
      validDetails.setMessage(message);
    } else {
      message.put("default", ex.getLocalizedMessage());
      validDetails.setMessage(message);
    }
    validDetails.setStatus(Integer.valueOf(HttpStatus.BAD_REQUEST.value()));
    validDetails.setTimestamp(new Date());
    validDetails.setError("Not valid exception");
    validDetails.setPath(((ServletWebRequest) request).getRequest().getServletPath());
    return new ResponseEntity(validDetails, status);
  }

  private void sendNotification(Exception ex, HttpServletRequest request) {
    try {
      String logLevel = "ERROR";
      String msg = "";
      if (ex instanceof ExceptionHandle) {
        ExceptionHandle exceptionOm = (ExceptionHandle) ex;
        if (exceptionOm.status.value() < 500) return;
        msg = msg + "<b>ERROR</b> : " + ex.getMessage() + "(" + exceptionOm.status.value() + ") \n";
      } else {
        msg = msg + "<b>ERROR</b> : " + ex.getMessage() + " \n";
      }
      try {
        msg = msg + "<b>METHOD</b> : " + request.getMethod() + " \n";
      } catch (Exception exception) {
      }
      try {
        msg = msg + "<b>URI</b> : " + request.getRequestURL().toString() + " \n";
      } catch (Exception exception) {
      }
      try {
        msg = msg + "<b>BODY</b> : " + getBody(request);
      } catch (Exception exception) {
      }
    } catch (Exception exception) {
    }
  }

  public static String getBody(HttpServletRequest request) throws IOException {
    String body = null;
    StringBuilder stringBuilder = new StringBuilder();
    BufferedReader bufferedReader = null;
    try {
      ServletInputStream servletInputStream = request.getInputStream();
      if (servletInputStream != null) {
        bufferedReader =
            new BufferedReader(new InputStreamReader((InputStream) servletInputStream));
        char[] charBuffer = new char[128];
        int bytesRead = -1;
        while ((bytesRead = bufferedReader.read(charBuffer)) > 0)
          stringBuilder.append(charBuffer, 0, bytesRead);
      } else {
        stringBuilder.append("");
      }
    } catch (IOException ex) {
      throw ex;
    } finally {
      if (bufferedReader != null)
        try {
          bufferedReader.close();
        } catch (IOException ex) {
          throw ex;
        }
    }
    body = stringBuilder.toString();
    return body;
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {
    ExceptionResponse exceptionResponse =
        new ExceptionResponse(
            HttpStatus.BAD_REQUEST,
            new Date(),
            "Malformed JSON request",
            "MALFORMED_JSON_REQUEST",
            ex.getLocalizedMessage(),
            ((ServletWebRequest) request).getRequest().getServletPath());
    return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({MissingRequestHeaderException.class})
  public ResponseEntity<ExceptionResponse> handleException(MissingRequestHeaderException ex) {
    if (ex.getMessage().contains("x-auth-token")) {
      ExceptionResponse exceptionResponse =
          new ExceptionResponse(
              HttpStatus.UNAUTHORIZED,
              new Date(),
              "unauthorized",
              "UNAUTHORIZED",
              "unauthorized",
              null);
      return new ResponseEntity(exceptionResponse, HttpStatus.UNAUTHORIZED);
    }
    ExceptionResponse errorDetails =
        new ExceptionResponse(
            HttpStatus.BAD_REQUEST,
            new Date(),
            ex.getMessage(),
            "MISSING_REQUEST_HEADER",
            ex.getMessage(),
            null);
    return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
  }
}
