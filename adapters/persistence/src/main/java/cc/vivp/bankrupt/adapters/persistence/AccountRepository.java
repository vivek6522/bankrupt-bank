package cc.vivp.bankrupt.adapters.persistence;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
interface AccountRepository extends Neo4jRepository<AccountNodeEntity, String> {}
