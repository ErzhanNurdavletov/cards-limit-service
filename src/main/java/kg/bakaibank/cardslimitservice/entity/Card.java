package kg.bakaibank.cardslimitservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Generated;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Card {

    @Id
    @Column(name = "card_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(name = "masked_pan", nullable = false)
    private String maskedPan;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "status", nullable = false)
    @Generated
    private String status;

    @ManyToMany
    @JoinTable(
        name = "cards_limits",
        joinColumns = @JoinColumn(name = "card_id"),
        inverseJoinColumns = @JoinColumn(name = "limit_id")
    )
    private Set<Limit> limits = new HashSet<>();

    @Column(name = "opened_at", nullable = false)
    private OffsetDateTime openedAt;

    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;

    public void addLimit(Limit limit) {
        limits.add(limit);
        limit.getCards().add(this);
    }

    public void removeLimit(Limit limit) {
        limits.remove(limit);
        limit.getCards().remove(this);
    }
}
