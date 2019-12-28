package cc.vivp.bankrupt.payments.application;

import java.util.ArrayList;
import java.util.List;
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

  @ApiOperation(value = "Fetch outgoing payments for IBAN")
  @GetMapping("iban/{iban}/out")
  public List<Payment> findOutgoingPaymentsFor(@PathVariable("iban") String iban) {
    List<Payment> paymentsForDebtor = new ArrayList<>();
    paymentRepository.findOutgoingPaymentsFor(iban).stream()
        .forEach(payment -> paymentsForDebtor.add(new Payment(payment)));
    return paymentsForDebtor;
  }

  @ApiOperation(value = "Fetch incoming payments for IBAN")
  @GetMapping("iban/{iban}/in")
  public List<Payment> findIncomingPaymentsFor(@PathVariable("iban") String iban) {
    List<Payment> paymentsForCreditor = new ArrayList<>();
    paymentRepository.findIncomingPaymentsFor(iban).stream()
        .forEach(payment -> paymentsForCreditor.add(new Payment(payment)));
    return paymentsForCreditor;
  }

  @ApiOperation(value = "Fetch all payments for IBAN")
  @GetMapping("iban/{iban}/all")
  public List<Payment> findAllPaymentsFor(@PathVariable("iban") String iban) {
    List<Payment> paymentsForIban = new ArrayList<>();
    paymentRepository.findAllPaymentsFor(iban).stream()
        .forEach(payment -> paymentsForIban.add(new Payment(payment)));
    return paymentsForIban;
  }

}
