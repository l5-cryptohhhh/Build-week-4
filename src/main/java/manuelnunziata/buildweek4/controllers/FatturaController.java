package manuelnunziata.buildweek4.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import manuelnunziata.buildweek4.entities.Fattura;
import manuelnunziata.buildweek4.entities.StatoFattura;
import manuelnunziata.buildweek4.entities.Utenti;
import manuelnunziata.buildweek4.payloads.CambiaStatoFatturaDTO;
import manuelnunziata.buildweek4.payloads.FatturaDTO;
import manuelnunziata.buildweek4.services.FatturaService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fatture")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'COMMERCIALE', 'CONTABILE')")
public class FatturaController {

    private final FatturaService fatturaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Fattura create(@RequestBody @Valid FatturaDTO dto, @AuthenticationPrincipal Utenti richiedente) {
        return fatturaService.create(dto, richiedente);
    }

    @GetMapping
    public List<Fattura> findAll(@RequestParam(required = false) Long clienteId,
                                  @RequestParam(required = false) StatoFattura stato,
                                  @AuthenticationPrincipal Utenti richiedente) {
        return fatturaService.findAll(richiedente, clienteId, stato);
    }

    @GetMapping("/{id}")
    public Fattura findById(@PathVariable Long id, @AuthenticationPrincipal Utenti richiedente) {
        return fatturaService.findById(id, richiedente);
    }

    @PutMapping("/{id}")
    public Fattura update(@PathVariable Long id, @RequestBody @Valid FatturaDTO dto, @AuthenticationPrincipal Utenti richiedente) {
        return fatturaService.update(id, dto, richiedente);
    }

    @PatchMapping("/{id}/stato")
    @PreAuthorize("hasAnyRole('ADMIN', 'CONTABILE')")
    public Fattura cambiaStato(@PathVariable Long id, @RequestBody @Valid CambiaStatoFatturaDTO dto, @AuthenticationPrincipal Utenti richiedente) {
        return fatturaService.cambiaStato(id, dto.stato(), richiedente);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id, @AuthenticationPrincipal Utenti richiedente) {
        fatturaService.delete(id, richiedente);
    }
}
