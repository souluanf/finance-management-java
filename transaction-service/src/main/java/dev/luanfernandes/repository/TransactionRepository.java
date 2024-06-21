package dev.luanfernandes.repository;

import dev.luanfernandes.domain.entity.Transaction;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByFromUserIdOrToUserId(Long fromUserId, Long toUserId);

    List<Transaction> findByFromUserId(Long fromUserId);

    List<Transaction> findByToUserId(Long toUserId);
}
