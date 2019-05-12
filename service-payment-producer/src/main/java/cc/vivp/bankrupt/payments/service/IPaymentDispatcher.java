package cc.vivp.bankrupt.payments.service;

import cc.vivp.bankrupt.payments.models.api.Payment;
import cc.vivp.bankrupt.payments.models.api.PaymentDispatchException;

@FunctionalInterface
public interface IPaymentDispatcher {

  Payment dispatchPayment(Payment payment) throws PaymentDispatchException;
}
