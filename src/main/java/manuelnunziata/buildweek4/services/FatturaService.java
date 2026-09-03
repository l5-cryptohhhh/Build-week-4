package manuelnunziata.buildweek4.services;

import lombok.RequiredArgsConstructor;
import manuelnunziata.buildweek4.entities.Cliente;
import manuelnunziata.buildweek4.entities.Fattura;
import manuelnunziata.buildweek4.entities.StatoFattura;
import manuelnunziata.buildweek4.entities.Utenti;
import manuelnunziata.buildweek4.exceptions.BadRequestException;
import manuelnunziata.buildweek4.exceptions.NotFoundException;
import manuelnunziata.buildweek4.payloads.FatturaDTO;
import manuelnunziata.buildweek4.repositories.FatturaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FatturaService {

    private static final Map<StatoFattura, Set<StatoFattura>> TRANSIZIONI_PERMESSE = Map.of(
            StatoFattura.BOZZA, Set.of(StatoFattura.EMESSA),
            StatoFattura.EMESSA, Set.of(StatoFattura.PAGATA, StatoFattura.SCADUTA),
            StatoFattura.SCADUTA, Set.of(StatoFattura.INSOLUTA, StatoFattura.PAGATA),
            StatoFattura.PAGATA, Set.of(),
            StatoFattura.INSOLUTA, Set.of()
    );

    private final FatturaRepository fatturaRepository;
    private final ClienteService clienteService;

    public Fattura create(FatturaDTO dto, Utenti richiedente) {
        Cliente cliente = clienteService.findById(dto.clienteId(), richiedente);

        Fattura fattura = new Fattura();
        fattura.setNumero(dto.numero());
        fattura.setImporto(dto.importo());
        fattura.setScadenza(dto.scadenza());
        fattura.setCliente(cliente);
        return fatturaRepository.save(fattura);
    }

    public List<Fattura> findAll(Utenti richiedente, Long clienteId, StatoFattura stato) {
        List<Fattura> fatture = clienteId != null
                ? fatturaRepository.findByClienteId(clienteId)
                : stato != null
                ? fatturaRepository.findByStato(stato)
                : fatturaRepository.findAll();

        return fatture.stream().filter(f -> clienteService.hasAccess(f.getCliente(), richiedente)).toList();
    }

    public Fattura findById(Long id, Utenti richiedente) {
        Fattura fattura = fatturaRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        clienteService.findById(fattura.getCliente().getId(), richiedente);
        return fattura;
    }

    public Fattura update(Long id, FatturaDTO dto, Utenti richiedente) {
        Fattura fattura = findById(id, richiedente);
        if (fattura.getStato() != StatoFattura.BOZZA) {
            throw new BadRequestException("Una fattura può essere modificata solo mentre è in BOZZA");
        }
        fattura.setNumero(dto.numero());
        fattura.setImporto(dto.importo());
        fattura.setScadenza(dto.scadenza());
        return fatturaRepository.save(fattura);
    }

    public Fattura cambiaStato(Long id, StatoFattura nuovoStato, Utenti richiedente) {
        Fattura fattura = findById(id, richiedente);
        Set<StatoFattura> permessi = TRANSIZIONI_PERMESSE.get(fattura.getStato());

        if (!permessi.contains(nuovoStato)) {
            throw new BadRequestException("Transizione non permessa da " + fattura.getStato() + " a " + nuovoStato);
        }

        fattura.setStato(nuovoStato);
        return fatturaRepository.save(fattura);
    }

    public void delete(Long id, Utenti richiedente) {
        Fattura fattura = findById(id, richiedente);
        if (fattura.getStato() != StatoFattura.BOZZA) {
            throw new BadRequestException("Una fattura può essere eliminata solo mentre è in BOZZA");
        }
        fatturaRepository.delete(fattura);
    }
}