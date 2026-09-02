package manuelnunziata.buildweek4.services;

import lombok.RequiredArgsConstructor;
import manuelnunziata.buildweek4.entities.Cliente;
import manuelnunziata.buildweek4.entities.Fattura;
import manuelnunziata.buildweek4.entities.StatoFattura;
import manuelnunziata.buildweek4.entities.Utenti;
import manuelnunziata.buildweek4.exceptions.NotFoundException;
import manuelnunziata.buildweek4.payloads.FatturaDTO;
import manuelnunziata.buildweek4.repositories.FatturaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.Map;

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
}
