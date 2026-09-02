package manuelnunziata.buildweek4.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import manuelnunziata.buildweek4.entities.Indirizzo;
import manuelnunziata.buildweek4.payloads.IndirizzoDTO;
import manuelnunziata.buildweek4.services.IndirizzoService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/indirizzi")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'COMMERCIALE')")
public class IndirizzoController {

    private final IndirizzoService indirizzoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Indirizzo create(@RequestBody @Valid IndirizzoDTO dto) {
        return indirizzoService.create(dto);
    }

    @GetMapping
    public List<Indirizzo> findAll() {
        return indirizzoService.findAll();
    }

    @GetMapping("/{id}")
    public Indirizzo findById(@PathVariable Long id) {
        return indirizzoService.findById(id);
    }

    @PutMapping("/{id}")
    public Indirizzo update(@PathVariable Long id, @RequestBody @Valid IndirizzoDTO dto) {
        return indirizzoService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        indirizzoService.delete(id);
    }
}
