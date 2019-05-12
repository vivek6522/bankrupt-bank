package cc.vivp.bankrupt.contracts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import cc.vivp.bankrupt.contracts.models.db.Contract;

@Repository
public interface ContractRepository extends JpaRepository<Contract, String> {
}
