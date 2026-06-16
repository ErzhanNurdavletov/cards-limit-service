package kg.bakaibank.cardslimitservice.repository;

import kg.bakaibank.cardslimitservice.entity.CardIssueType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CardIssueTypeRepository extends JpaRepository<CardIssueType, UUID> {
    Optional<CardIssueType> findByNameIgnoreCase(String name);
}
