package cc.vivp.bankrupt.event;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cc.vivp.bankrupt.exception.DomainException;
import cc.vivp.bankrupt.model.AccountType;
import cc.vivp.bankrupt.model.api.TransferCommand;
import cc.vivp.bankrupt.model.api.TransferReceipt;
import cc.vivp.bankrupt.model.db.AccountEntity;
import cc.vivp.bankrupt.repository.AccountsRepository;
import cc.vivp.bankrupt.repository.TransfersRepository;
import java.time.LocalDateTime;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

    @BeforeEach
    void setup() {
        accountsRepository.save(new AccountEntity("1", AccountType.CURRENT, 1L, 100L));
        accountsRepository.save(new AccountEntity("2", AccountType.CURRENT, 2L, 100L));
        accountsRepository.save(new AccountEntity("3", AccountType.SAVINGS, 2L, 0L));
    }

    @Test
    void shouldTransferGivenValidInputs() throws DomainException {

        TransferCommand transferCommand = new TransferCommand("1", "2", 1L, "test");

        TransferReceipt transferReceipt = new TransferEvent(LocalDateTime.now(), transferCommand, accountsRepository,
            transfersRepository).process();

        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(transferReceipt.getSource()).isEqualTo(transferCommand.getSource());
        assertions.assertThat(transferReceipt.getTarget()).isEqualTo(transferCommand.getTarget());
        assertions.assertThat(transferReceipt.getAmount()).isEqualTo(transferCommand.getAmount());
        assertions.assertThat(transferReceipt.getDescription()).isEqualTo(transferCommand.getDescription());
        assertions.assertAll();
    }
}
