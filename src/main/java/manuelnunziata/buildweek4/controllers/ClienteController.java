package manuelnunziata.buildweek4.controllers;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import manuelnunziata.buildweek4.entities.Cliente;
import manuelnunziata.buildweek4.entities.Utenti;
import manuelnunziata.buildweek4.paylods.ClienteDTO;
import manuelnunziata.buildweek4.services.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clienti")
@RequiredArgsConstructor

public class ClienteController {
    private final ClienteService clienteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN', 'COMMERCIALE')")
    public Cliente creazioneCliente(@RequestBody @Valid ClienteDTO dto, @AuthenticationPrincipal Utenti richiedente) {
        return clienteService.creazione(dto, richiedente);
    }
}

