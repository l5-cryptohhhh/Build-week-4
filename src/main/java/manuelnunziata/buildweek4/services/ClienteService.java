package manuelnunziata.buildweek4.services;


import lombok.RequiredArgsConstructor;
import manuelnunziata.buildweek4.entities.Cliente;
import manuelnunziata.buildweek4.entities.Indirizzo;
import manuelnunziata.buildweek4.entities.Ruolo;
import manuelnunziata.buildweek4.entities.Utenti;
import manuelnunziata.buildweek4.exceptions.NotFoundException;
import manuelnunziata.buildweek4.exceptions.UnauthorizedException;
import manuelnunziata.buildweek4.payloads.ClienteDTO;
import manuelnunziata.buildweek4.repositories.ClienteRepository;
import manuelnunziata.buildweek4.repositories.IndirizzoRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final IndirizzoRepository indirizzoRepository;
    //private final UtentiRepository utentiRepository;
    //private final FatturaRepository fatturaRepository;
    //private final NotaRepository notaRepository;

    public Cliente creazione(ClienteDTO dto, Utenti richiedente) {
        Indirizzo indirizzo = indirizzoRepository.findById(dto.indirizzoId())
                .orElseThrow(() -> new NotFoundException("Indirizzo con id " + dto.indirizzoId() + " non trovato"));
        Utenti commerciale = resolveCommerciale(dto, richiedente);
        Cliente cliente = new Cliente();
        applyDto(cliente, dto, indirizzo);
        cliente.setCommerciale(commerciale);
        return clienteRepository.save(cliente);
    }

    private void applyDto(Cliente cliente, ClienteDTO dto, Indirizzo indirizzo) {
        cliente.setRagioneSociale(dto.ragioneSociale());
        cliente.setPartitaIva(dto.partitaIva());
        cliente.setEmail(dto.email());
        cliente.setTelefono(dto.telefono());
        cliente.setIndirizzo(indirizzo);
    }

    private Utenti resolveCommerciale(ClienteDTO dto, Utenti richiedente) {
        if (richiedente.getRuolo() == Ruolo.COMMERCIALE) {
            return richiedente;
        }
        throw new UnauthorizedException("Solo un utente con ruolo COMMERCIALE può essere assegnato a un cliente");
    }
}
