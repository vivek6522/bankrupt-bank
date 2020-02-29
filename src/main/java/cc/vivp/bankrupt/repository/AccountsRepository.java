package cc.vivp.bankrupt.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cc.vivp.bankrupt.model.db.AccountEntity;

@Repository
public interface AccountsRepository extends PagingAndSortingRepository<AccountEntity, Long> {
  
  @Query("select a from Account as a where a.accountNumber = :accountNumber")
  AccountEntity findByAccountNumber(@Param("accountNumber") String accountNumber);

}
