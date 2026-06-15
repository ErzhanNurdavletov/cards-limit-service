package kg.bakaibank.cardslimitservice.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import kg.bakaibank.cardslimitservice.entity.enums.ClientType;

public record ClientUpdateRequest(
    @NotBlank(message = "firstname can't be empty or null or blank")
    @Size(min = 1, max = 50, message = "firstname must be minimum size = 1, max size = 50(include)")
    String firstname,

    @NotBlank(message = "lastname can't be empty or null or blank")
    @Size(min = 1, max = 50, message = "lastname must be minimum size = 1, max size = 50(include)")
    String lastname,

    @Size(max = 50, message = "patronymic max size = 50(include)")
    String patronymic,

    @NotNull(message = "type must be LEGAL or INDIVIDUAL")
    ClientType type,

    @NotBlank(message = "phoneNumber can't be empty or null or blank")
    @Pattern(regexp = "^(?:\\+996|996|0)?[0-9]{9}$", message = "phoneNumber must be Kyrgyzstan number, only + and digits")
    String phoneNumber
) {
}