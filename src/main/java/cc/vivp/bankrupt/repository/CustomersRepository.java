package cc.vivp.bankrupt.repository;

import cc.vivp.bankrupt.model.db.CustomerEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomersRepository extends PagingAndSortingRepository<CustomerEntity, Long> {

    @Query("select c from Customer as c where c.email = :email")
    Optional<CustomerEntity> findByEmail(@Param("email") String email);

}
