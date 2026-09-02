package manuelnunziata.buildweek4.paylods;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClienteDTO(
        @NotBlank(message = "La ragione sociale è obbligatoria") String ragioneSociale,
        @NotBlank(message = "La partita IVA è obbligatoria") String partitaIva,
        @NotBlank(message = "L'email è obbligatoria") String email,
        @NotBlank(message = "Il telefono è obbligatorio") String telefono,
        @NotNull(message = "L'indirizzo è obbligatorio") Long indirizzoId
) {
}
