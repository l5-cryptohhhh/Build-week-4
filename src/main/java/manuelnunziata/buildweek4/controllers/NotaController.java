package manuelnunziata.buildweek4.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import manuelnunziata.buildweek4.entities.Nota;
import manuelnunziata.buildweek4.entities.Utenti;
import manuelnunziata.buildweek4.payloads.NotaDTO;
import manuelnunziata.buildweek4.services.NotaService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clienti/{clienteId}/note")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'COMMERCIALE')")
public class NotaController {

    private final NotaService notaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Nota create(@PathVariable Long clienteId, @RequestBody @Valid NotaDTO dto, @AuthenticationPrincipal Utenti richiedente) {
        return notaService.create(clienteId, dto, richiedente);
    }

    @GetMapping
    public List<Nota> findAll(@PathVariable Long clienteId, @AuthenticationPrincipal Utenti richiedente) {
        return notaService.findAllByCliente(clienteId, richiedente);
    }

    @GetMapping("/{notaId}")
    public Nota findById(@PathVariable Long clienteId, @PathVariable Long notaId, @AuthenticationPrincipal Utenti richiedente) {
        return notaService.findById(clienteId, notaId, richiedente);
    }

    @PutMapping("/{notaId}")
    public Nota update(@PathVariable Long clienteId, @PathVariable Long notaId, @RequestBody @Valid NotaDTO dto, @AuthenticationPrincipal Utenti richiedente) {
        return notaService.update(clienteId, notaId, dto, richiedente);
    }

    @DeleteMapping("/{notaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long clienteId, @PathVariable Long notaId, @AuthenticationPrincipal Utenti richiedente) {
        notaService.delete(clienteId, notaId, richiedente);
    }
}