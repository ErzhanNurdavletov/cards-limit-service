package kg.bakaibank.cardslimitservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Limit {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "limit_id")
    private UUID id;

    @ManyToMany(mappedBy = "limits")
    private Set<Card> cards = new HashSet<>();

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "max_amount", precision = 15, scale = 2, nullable = false)
    private BigDecimal maxAmount;

    @Column(name = "max_count", nullable = false)
    private Integer maxCount;

    @Column(name = "default_amount", nullable = false)
    private BigDecimal defaultAmount = new BigDecimal("50000.00");

    @Column(name = "default_count", nullable = false)
    private Integer defaultCount = 2000;

    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;
}
