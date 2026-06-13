package kg.bakaibank.cardslimitservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
public class CardCustomLimitCompositeKey implements Serializable {

    @Column(name = "card_id")
    private UUID cardId;

    @Column(name = "limit_id")
    private UUID limitId;
}
