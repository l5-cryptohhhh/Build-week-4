package manuelnunziata.buildweek4.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import manuelnunziata.buildweek4.entities.Cliente;
import manuelnunziata.buildweek4.entities.Utenti;
import manuelnunziata.buildweek4.payloads.ClienteDTO;
import manuelnunziata.buildweek4.services.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clienti")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN', 'COMMERCIALE')")
    public Cliente create(@RequestBody @Valid ClienteDTO dto, @AuthenticationPrincipal Utenti richiedente) {
        return clienteService.create(dto, richiedente);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'COMMERCIALE', 'CONTABILE')")
    public List<Cliente> findAll(@RequestParam(required = false) String search, @AuthenticationPrincipal Utenti richiedente) {
        return clienteService.findAll(richiedente, search);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COMMERCIALE', 'CONTABILE')")
    public Cliente findById(@PathVariable Long id, @AuthenticationPrincipal Utenti richiedente) {
        return clienteService.findById(id, richiedente);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COMMERCIALE')")
    public Cliente update(@PathVariable Long id, @RequestBody @Valid ClienteDTO dto, @AuthenticationPrincipal Utenti richiedente) {
        return clienteService.update(id, dto, richiedente);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ADMIN', 'COMMERCIALE')")
    public void delete(@PathVariable Long id, @AuthenticationPrincipal Utenti richiedente) {
        clienteService.delete(id, richiedente);
    }
}


