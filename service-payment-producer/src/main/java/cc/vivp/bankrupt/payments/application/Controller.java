package cc.vivp.bankrupt.payments.application;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cc.vivp.bankrupt.payments.models.api.Payment;
import cc.vivp.bankrupt.payments.models.api.PaymentDispatchException;
import cc.vivp.bankrupt.payments.service.IPaymentDispatcher;

@RestController
@RequestMapping("/payments")
public class Controller {

  private final IPaymentDispatcher paymentDispatcher;

  @Autowired
  public Controller(IPaymentDispatcher paymentDispatcher) {
    this.paymentDispatcher = paymentDispatcher;
  }

  @PostMapping
  public Payment initiatePayment(@Valid @RequestBody Payment payment) throws PaymentDispatchException {
    return paymentDispatcher.dispatchPayment(payment);
  }

}
