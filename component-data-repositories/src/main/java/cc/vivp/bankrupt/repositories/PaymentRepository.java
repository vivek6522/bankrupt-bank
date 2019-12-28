package cc.vivp.bankrupt.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import cc.vivp.bankrupt.payments.models.db.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {

  @Query("select p from Payment as p where p.debtorIban = :debtorIban")
  List<Payment> findOutgoingPaymentsFor(@Param("debtorIban") String debtorIban);

  @Query("select p from Payment as p where p.creditorIban = :creditorIban")
  List<Payment> findIncomingPaymentsFor(@Param("creditorIban") String creditorIban);

  @Query("select p from Payment as p where p.debtorIban = :iban or p.creditorIban = :iban")
  List<Payment> findAllPaymentsFor(@Param("iban") String iban);

}
