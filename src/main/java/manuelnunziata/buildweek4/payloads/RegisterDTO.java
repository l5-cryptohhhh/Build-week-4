package manuelnunziata.buildweek4.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterDTO(
        @NotBlank(message = "L'email è obbligatoria") @Email(message = "Email non valida") String email,
        @NotBlank(message = "La password è obbligatoria") String password,
        @NotBlank(message = "Il nome è obbligatorio") String nome,
        @NotBlank(message = "Il cognome è obbligatorio") String cognome
) {
}
