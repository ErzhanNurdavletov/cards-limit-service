package kg.bakaibank.cardslimitservice.repository;

import kg.bakaibank.cardslimitservice.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {
    Optional<Client> findClientById(UUID id);
    Optional<Client> findByDeletedAtIsNullAndId(UUID id);
}
