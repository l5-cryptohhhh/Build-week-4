package manuelnunziata.buildweek4.payloads;

import jakarta.validation.constraints.NotNull;
import manuelnunziata.buildweek4.entities.StatoFattura;

public record CambiaStatoFatturaDTO(@NotNull(message = "Lo stato è obbligatorio") StatoFattura stato) {
}
