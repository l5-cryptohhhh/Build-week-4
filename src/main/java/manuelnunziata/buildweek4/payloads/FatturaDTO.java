package manuelnunziata.buildweek4.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record FatturaDTO(
        @NotBlank(message = "Il numero è obbligatorio") String numero,
        @NotNull(message = "L'importo è obbligatorio") @Positive(message = "L'importo deve essere positivo") BigDecimal importo,
        @NotNull(message = "La scadenza è obbligatoria") LocalDate scadenza,
        @NotNull(message = "Il cliente è obbligatorio") Long clienteId
) {
}
