package cc.vivp.bankrupt.transactions.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cc.vivp.bankrupt.repositories.PaymentRepository;
import cc.vivp.bankrupt.repositories.TransactionRepository;
import cc.vivp.bankrupt.transactions.models.api.Transaction;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("transactions")
public class Controller {

  private final TransactionRepository transactionRepository;
  private final PaymentRepository paymentRepository;

  @Autowired
  public Controller(TransactionRepository transactionRepository,
      PaymentRepository paymentRepository) {
    this.transactionRepository = transactionRepository;
    this.paymentRepository = paymentRepository;
  }

  @ApiOperation(value = "Fetch transactions for account")
  @GetMapping("{iban}")
  public List<Transaction> fetchTransactionsForAccount(@PathVariable("iban") String iban) {

    List<cc.vivp.bankrupt.payments.models.db.Payment> paymentsForDebtor =
        paymentRepository.findPaymentsForDebtor(iban);

    return paymentsForDebtor.stream().map(payment -> {
      cc.vivp.bankrupt.transactions.models.db.Transaction transaction =
          transactionRepository.getOne(payment.getId());
      return new Transaction(transaction);
    }).collect(Collectors.toList());
  }

}
