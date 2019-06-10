package cc.vivp.bankrupt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import cc.vivp.bankrupt.payments.models.db.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {
}
