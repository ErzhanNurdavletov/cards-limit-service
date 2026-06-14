package kg.bakaibank.cardslimitservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode(of = {"cardId", "limitId"})
public class CardCustomLimitCompositeKey implements Serializable {

    @Column(name = "card_id")
    private UUID cardId;

    @Column(name = "limit_id")
    private UUID limitId;
}
