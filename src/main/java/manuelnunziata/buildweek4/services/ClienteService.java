package manuelnunziata.buildweek4.services;

import lombok.RequiredArgsConstructor;
import manuelnunziata.buildweek4.entities.Cliente;
import manuelnunziata.buildweek4.entities.Indirizzo;
import manuelnunziata.buildweek4.entities.Ruolo;
import manuelnunziata.buildweek4.entities.Utenti;
import manuelnunziata.buildweek4.exceptions.BadRequestException;
import manuelnunziata.buildweek4.exceptions.NotFoundException;
import manuelnunziata.buildweek4.payloads.ClienteDTO;
import manuelnunziata.buildweek4.repositories.ClienteRepository;
import manuelnunziata.buildweek4.repositories.FatturaRepository;
import manuelnunziata.buildweek4.repositories.IndirizzoRepository;
import manuelnunziata.buildweek4.repositories.NotaRepository;
import manuelnunziata.buildweek4.repositories.UtentiRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final IndirizzoRepository indirizzoRepository;
    private final UtentiRepository utentiRepository;
    private final FatturaRepository fatturaRepository;
    private final NotaRepository notaRepository;

    public Cliente create(ClienteDTO dto, Utenti richiedente) {
        Indirizzo indirizzo = indirizzoRepository.findById(dto.indirizzoId())
                .orElseThrow(() -> new NotFoundException(dto.indirizzoId()));
        Utenti commerciale = resolveCommerciale(dto, richiedente);

        Cliente cliente = new Cliente();
        applyDto(cliente, dto, indirizzo);
        cliente.setCommerciale(commerciale);
        return clienteRepository.save(cliente);
    }

    public List<Cliente> findAll(Utenti richiedente, String search) {
        List<Cliente> clienti = (search == null || search.isBlank())
                ? clienteRepository.findAll()
                : clienteRepository.findByRagioneSocialeContainingIgnoreCaseOrPartitaIvaContainingIgnoreCase(search, search);

        if (richiedente.getRuolo() == Ruolo.COMMERCIALE) {
            return clienti.stream().filter(c -> c.getCommerciale().getId().equals(richiedente.getId())).toList();
        }
        return clienti;
    }

    public Cliente findById(Long id, Utenti richiedente) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        checkOwnership(cliente, richiedente);
        return cliente;
    }

    public Cliente update(Long id, ClienteDTO dto, Utenti richiedente) {
        Cliente cliente = findById(id, richiedente);
        Indirizzo indirizzo = indirizzoRepository.findById(dto.indirizzoId())
                .orElseThrow(() -> new NotFoundException(dto.indirizzoId()));
        applyDto(cliente, dto, indirizzo);
        return clienteRepository.save(cliente);
    }

    public void delete(Long id, Utenti richiedente) {
        Cliente cliente = findById(id, richiedente);
        if (fatturaRepository.existsByClienteId(id) || notaRepository.existsByClienteId(id)) {
            throw new BadRequestException("Impossibile eliminare il cliente: ha fatture o note associate");
        }
        clienteRepository.delete(cliente);
    }

    private Utenti resolveCommerciale(ClienteDTO dto, Utenti richiedente) {
        if (richiedente.getRuolo() == Ruolo.COMMERCIALE) {
            return richiedente;
        }
        if (dto.commercialeId() == null) {
            throw new BadRequestException("Specificare il commerciale a cui assegnare il cliente");
        }
        return utentiRepository.findById(dto.commercialeId())
                .orElseThrow(() -> new NotFoundException(dto.commercialeId()));
    }

    public boolean hasAccess(Cliente cliente, Utenti richiedente) {
        return richiedente.getRuolo() != Ruolo.COMMERCIALE || cliente.getCommerciale().getId().equals(richiedente.getId());
    }

    private void checkOwnership(Cliente cliente, Utenti richiedente) {
        if (!hasAccess(cliente, richiedente)) {
            throw new AccessDeniedException("Non sei il commerciale assegnato a questo cliente");
        }
    }

    private void applyDto(Cliente cliente, ClienteDTO dto, Indirizzo indirizzo) {
        cliente.setRagioneSociale(dto.ragioneSociale());
        cliente.setPartitaIva(dto.partitaIva());
        cliente.setEmail(dto.email());
        cliente.setTelefono(dto.telefono());
        cliente.setIndirizzo(indirizzo);
    }
}
