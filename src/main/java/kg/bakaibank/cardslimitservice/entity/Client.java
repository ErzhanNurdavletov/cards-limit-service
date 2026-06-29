package kg.bakaibank.cardslimitservice.entity;

import jakarta.persistence.*;
import kg.bakaibank.cardslimitservice.entity.enums.ClientType;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "clients")
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @Column(name = "client_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "firstname", nullable = false, length = 50)
    private String firstname;

    @Column(name = "lastname", nullable = false, length = 50)
    private String lastname;

    @Column(name = "patronymic")
    private String patronymic;

    @OneToMany(mappedBy = "client")
    private Set<Card> cards;

    @Column(name = "client_type", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private ClientType type;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;
}
