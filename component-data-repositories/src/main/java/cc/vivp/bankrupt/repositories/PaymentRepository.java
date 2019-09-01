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
  List<Payment> findPaymentsForDebtor(@Param("debtorIban") String debtorIban);
}
