package manuelnunziata.buildweek4.payloads;

import jakarta.validation.constraints.NotBlank;

public record NotaDTO(@NotBlank(message = "Il testo della nota è obbligatorio") String testo) {
}
