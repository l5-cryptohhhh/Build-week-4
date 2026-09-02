package manuelnunziata.buildweek4.services;

import lombok.RequiredArgsConstructor;
import manuelnunziata.buildweek4.entities.Cliente;
import manuelnunziata.buildweek4.entities.Nota;
import manuelnunziata.buildweek4.entities.Utenti;
import manuelnunziata.buildweek4.exceptions.NotFoundException;
import manuelnunziata.buildweek4.payloads.NotaDTO;
import manuelnunziata.buildweek4.repositories.NotaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotaService {

    private final NotaRepository notaRepository;
    private final ClienteService clienteService;

    public Nota create(Long clienteId, NotaDTO dto, Utenti richiedente) {
        Cliente cliente = clienteService.findById(clienteId, richiedente);
        Nota nota = new Nota();
        nota.setTesto(dto.testo());
        nota.setCliente(cliente);
        return notaRepository.save(nota);
    }

    public List<Nota> findAllByCliente(Long clienteId, Utenti richiedente) {
        clienteService.findById(clienteId, richiedente);
        return notaRepository.findByClienteId(clienteId);
    }

    public Nota findById(Long clienteId, Long notaId, Utenti richiedente) {
        clienteService.findById(clienteId, richiedente);
        Nota nota = notaRepository.findById(notaId).orElseThrow(() -> new NotFoundException(notaId));
        if (!nota.getCliente().getId().equals(clienteId)) {
            throw new NotFoundException(notaId);
        }
        return nota;
    }

    public Nota update(Long clienteId, Long notaId, NotaDTO dto, Utenti richiedente) {
        Nota nota = findById(clienteId, notaId, richiedente);
        nota.setTesto(dto.testo());
        return notaRepository.save(nota);
    }

    public void delete(Long clienteId, Long notaId, Utenti richiedente) {
        Nota nota = findById(clienteId, notaId, richiedente);
        notaRepository.delete(nota);
    }
}
