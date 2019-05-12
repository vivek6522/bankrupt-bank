package cc.vivp.bankrupt.payments.models.api;

public class PaymentDispatchException extends Exception {

  private static final long serialVersionUID = 1_0_0L;

  public PaymentDispatchException(String message) {
    super(message);
  }
}
