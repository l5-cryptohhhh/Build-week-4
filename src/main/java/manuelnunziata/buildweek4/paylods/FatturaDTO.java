package manuelnunziata.buildweek4.paylods;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record FatturaDTO(
        @NotBlank(message = "Il numero fattura è obbligatorio") String numero,
        @Positive(message = "L'importo deve essere positivo") int importo,
        @NotNull(message = "La scadenza è obbligatoria") LocalDate scadenza,
        @NotNull(message = "Il cliente è obbligatorio") Long clienteId
) {
}
