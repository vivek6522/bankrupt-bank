package cc.vivp.bankrupt.payments.application;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cc.vivp.bankrupt.payments.models.api.Payment;
import cc.vivp.bankrupt.payments.models.api.PaymentDispatchException;
import cc.vivp.bankrupt.payments.service.IPaymentDispatcher;
import cc.vivp.bankrupt.repositories.PaymentRepository;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("payments")
public class Controller {

  private final IPaymentDispatcher paymentDispatcher;
  private final PaymentRepository paymentRepository;

  @Autowired
  public Controller(IPaymentDispatcher paymentDispatcher, PaymentRepository paymentRepository) {
    this.paymentDispatcher = paymentDispatcher;
    this.paymentRepository = paymentRepository;
  }

  @ApiOperation(value = "Initiate payment")
  @PostMapping
  public Payment initiatePayment(@Valid @RequestBody Payment payment)
      throws PaymentDispatchException {
    return paymentDispatcher.dispatchPayment(payment);
  }

  @ApiOperation(value = "Fetch payment details")
  @GetMapping("{paymentId}")
  public Payment fetchPaymentDetails(@PathVariable("paymentId") String paymentId) {
    return new Payment(paymentRepository.getOne(paymentId));
  }

}
