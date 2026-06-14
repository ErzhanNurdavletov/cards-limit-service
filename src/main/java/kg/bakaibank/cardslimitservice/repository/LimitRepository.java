package kg.bakaibank.cardslimitservice.repository;

import kg.bakaibank.cardslimitservice.entity.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LimitRepository extends JpaRepository<Limit, UUID> {
    Optional<Limit> findLimitById(UUID id);
    Optional<Limit> findLimitByName(String name);
    Optional<Limit> findByDeletedAtIsNullAndId(UUID id);
}
