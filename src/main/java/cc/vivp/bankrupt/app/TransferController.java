package cc.vivp.bankrupt.app;

import cc.vivp.bankrupt.event.TransferEvent;
import cc.vivp.bankrupt.exception.EntityNotFoundException;
import cc.vivp.bankrupt.model.api.TransferCommand;
import cc.vivp.bankrupt.model.api.TransferReceipt;
import cc.vivp.bankrupt.model.db.TransferEntity;
import cc.vivp.bankrupt.repository.AccountsRepository;
import cc.vivp.bankrupt.repository.TransfersRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class TransferController {

  private final AccountsRepository accountsRepository;
  private final TransfersRepository transfersRepository;
  private final ModelMapper modelMapper;

  @PostMapping("transfers")
  public TransferReceipt initiateFundsTransfer(@RequestBody TransferCommand transferCommand) {
    return new TransferEvent(LocalDateTime.now(), transferCommand, accountsRepository, transfersRepository).process();
  }

  @GetMapping("transfers/reference/{paymentReference}/{source}")
  public TransferReceipt fetchByPaymentReference(@PathVariable("paymentReference") String paymentReference,
      @PathVariable("source") String source)
      throws EntityNotFoundException {

    TransferEntity foundTransfer = transfersRepository.fetchByPaymentReferenceAndSource(paymentReference, source);

    if (foundTransfer == null) {
      throw new EntityNotFoundException(Collections.singletonMap("paymentReference", paymentReference));
    }

    return modelMapper.map(foundTransfer, TransferReceipt.class);
  }

  @GetMapping("transfers/account/{source}")
  public List<TransferReceipt> fetchTransactionHistory(@PathVariable("source") String source) {

    List<TransferReceipt> transactionHistory = new ArrayList<>();

    transfersRepository.fetchTransactionHistory(source).stream()
        .sorted(Comparator.comparing(TransferEntity::getTimestamp).reversed())
        .forEach(transferEntity -> transactionHistory.add(modelMapper.map(transferEntity, TransferReceipt.class)));

    return transactionHistory;
  }
}
