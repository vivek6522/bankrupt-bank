package cc.vivp.bankrupt.rest;

import cc.vivp.bankrupt.exception.DomainException;
import cc.vivp.bankrupt.exception.EntityNotFoundException;
import cc.vivp.bankrupt.model.api.TransferCommand;
import cc.vivp.bankrupt.model.api.TransferReceipt;
import cc.vivp.bankrupt.service.TransferService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class TransferController {

  private final TransferService transferService;

  @PostMapping("transfers")
  public TransferReceipt initiateFundsTransfer(@RequestBody TransferCommand transferCommand) throws DomainException {
    return transferService.initiateFundsTransfer(transferCommand);
  }

  @GetMapping("transfers/reference/{paymentReference}/{source}")
  public TransferReceipt fetchByPaymentReferenceAndSource(@PathVariable("paymentReference") String paymentReference,
      @PathVariable("source") String source)
      throws EntityNotFoundException {
    return transferService.fetchByPaymentReferenceAndSource(paymentReference, source);
  }

  @GetMapping("transfers/account/{source}")
  public List<TransferReceipt> fetchTransactionHistory(@PathVariable("source") String source) {
    return transferService.fetchTransactionHistory(source);
  }
}
