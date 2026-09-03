package manuelnunziata.buildweek4.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NotaDTO(
        @NotBlank(message = "Il testo della nota è obbligatorio") String testo,
        @NotNull(message = "Il cliente è obbligatorio") Long clienteId
) {
}