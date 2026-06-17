package kg.bakaibank.cardslimitservice.repository;

import kg.bakaibank.cardslimitservice.entity.CardCustomLimit;
import kg.bakaibank.cardslimitservice.entity.CardCustomLimitCompositeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface CardCustomLimitRepository extends JpaRepository<CardCustomLimit, UUID> {
    @Query("SELECT c FROM CardCustomLimit c JOIN FETCH c.limit WHERE c.id = :id")
    Optional<CardCustomLimit> findByIdWithLimitName(CardCustomLimitCompositeKey id);

    Optional<CardCustomLimit> findByCardIdAndLimitId(UUID cardId, UUID limitId);


    Optional<Set<CardCustomLimit>> findAllByCardId(UUID cardId);
}
