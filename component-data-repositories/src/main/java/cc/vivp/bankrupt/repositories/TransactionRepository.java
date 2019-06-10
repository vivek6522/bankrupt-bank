package cc.vivp.bankrupt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import cc.vivp.bankrupt.transactions.models.db.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
}
