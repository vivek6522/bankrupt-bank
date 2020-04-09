package cc.vivp.bankrupt.event;

import cc.vivp.bankrupt.exception.DomainException;
import cc.vivp.bankrupt.model.AccountType;
import cc.vivp.bankrupt.model.api.TransferCommand;
import cc.vivp.bankrupt.model.api.TransferReceipt;
import cc.vivp.bankrupt.model.db.AccountEntity;
import cc.vivp.bankrupt.model.db.CustomerEntity;
import cc.vivp.bankrupt.repository.AccountsRepository;
import cc.vivp.bankrupt.repository.CustomersRepository;
import cc.vivp.bankrupt.repository.TransfersRepository;
import java.time.LocalDateTime;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@DisplayName("Transfer event")
public class TransferEventTest {

    @Autowired
    private AccountsRepository accountsRepository;
    @Autowired
    private TransfersRepository transfersRepository;
    @Autowired
    private CustomersRepository customersRepository;

    @BeforeEach
    void setup() {
        CustomerEntity customer1 = customersRepository.save(new CustomerEntity("test1@test.com", "Test1"));
        CustomerEntity customer2 = customersRepository.save(new CustomerEntity("test2@test.com", "Test2"));
        accountsRepository.save(new AccountEntity("1", AccountType.CURRENT, 100L, customer1));
        accountsRepository.save(new AccountEntity("2", AccountType.CURRENT, 100L, customer2));
        accountsRepository.save(new AccountEntity("3", AccountType.SAVINGS, 0L, customer1));
    }

    @Test
    void shouldTransferGivenValidInputs() throws DomainException {

        TransferCommand transferCommand = new TransferCommand("1", "2", 1L, true, "test");

        TransferReceipt transferReceipt = new TransferEvent(LocalDateTime.now(), transferCommand, accountsRepository,
            transfersRepository, new ModelMapper()).process();

        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(transferReceipt.getSourceAccountNumber()).isEqualTo(transferCommand.getSource());
        assertions.assertThat(transferReceipt.getTargetAccountNumber()).isEqualTo(transferCommand.getTarget());
        assertions.assertThat(transferReceipt.getAmount()).isEqualTo(transferCommand.getAmount());
        assertions.assertThat(transferReceipt.getDescription()).isEqualTo(transferCommand.getDescription());
        assertions.assertAll();
    }
}
