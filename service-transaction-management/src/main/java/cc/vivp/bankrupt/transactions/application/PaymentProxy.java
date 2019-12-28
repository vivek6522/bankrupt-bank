package cc.vivp.bankrupt.transactions.application;

import java.util.List;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import cc.vivp.bankrupt.payments.models.api.Payment;

@FeignClient(name = "service-payment-producer")
@RibbonClient(name = "service-payment-producer")
public interface PaymentProxy {

  @GetMapping("payments/iban/{iban}/out")
  public List<Payment> findOutgoingPaymentsFor(@PathVariable("iban") String iban);

  @GetMapping("payments/iban/{iban}/in")
  public List<Payment> findIncomingPaymentsFor(@PathVariable("iban") String iban);

  @GetMapping("payments/iban/{iban}/all")
  public List<Payment> findAllPaymentsFor(@PathVariable("iban") String iban);
}
