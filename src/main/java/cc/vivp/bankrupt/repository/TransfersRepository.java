package cc.vivp.bankrupt.repository;

import cc.vivp.bankrupt.model.db.TransferEntity;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransfersRepository extends PagingAndSortingRepository<TransferEntity, Long> {

    @Query("select t from Transfer as t where t.paymentReference = :paymentReference and t.source = :source")
    TransferEntity fetchByPaymentReferenceAndSource(@Param("paymentReference") String paymentReference,
        @Param("source") String source);

    @Query("select t from Transfer as t where t.source = :source")
    List<TransferEntity> fetchTransactionHistory(@Param("source") String source);

}
