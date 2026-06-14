package kg.bakaibank.cardslimitservice.repository;

import kg.bakaibank.cardslimitservice.entity.CardCustomLimit;
import kg.bakaibank.cardslimitservice.entity.CardCustomLimitCompositeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CardCustomLimitRepository extends JpaRepository<CardCustomLimit, UUID> {
    Optional<CardCustomLimit> findById(CardCustomLimitCompositeKey id);
}
