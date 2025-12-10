package com.lpdev.financemanagerapi.exceptions;

public class FinanceManagerNotFoundException extends RuntimeException {
  public FinanceManagerNotFoundException(String message) {
    super(message);
  }
}
