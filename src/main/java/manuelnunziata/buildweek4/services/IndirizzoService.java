package manuelnunziata.buildweek4.services;

import lombok.RequiredArgsConstructor;
import manuelnunziata.buildweek4.entities.Indirizzo;
import manuelnunziata.buildweek4.exceptions.BadRequestException;
import manuelnunziata.buildweek4.exceptions.NotFoundException;
import manuelnunziata.buildweek4.payloads.IndirizzoDTO;
import manuelnunziata.buildweek4.repositories.ClienteRepository;
import manuelnunziata.buildweek4.repositories.IndirizzoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IndirizzoService {

    private final IndirizzoRepository indirizzoRepository;
    private final ClienteRepository clienteRepository;

    public Indirizzo create(IndirizzoDTO dto) {
        Indirizzo indirizzo = new Indirizzo();
        applyDto(indirizzo, dto);
        return indirizzoRepository.save(indirizzo);
    }

    public List<Indirizzo> findAll() {
        return indirizzoRepository.findAll();
    }

    public Indirizzo findById(Long id) {
        return indirizzoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    public Indirizzo update(Long id, IndirizzoDTO dto) {
        Indirizzo indirizzo = findById(id);
        applyDto(indirizzo, dto);
        return indirizzoRepository.save(indirizzo);
    }

    public void delete(Long id) {
        Indirizzo indirizzo = findById(id);
        if (clienteRepository.existsByIndirizzoId(id)) {
            throw new BadRequestException("Impossibile eliminare l'indirizzo: è in uso da almeno un cliente");
        }
        indirizzoRepository.delete(indirizzo);
    }

    private void applyDto(Indirizzo indirizzo, IndirizzoDTO dto) {
        indirizzo.setVia(dto.via());
        indirizzo.setCivico(dto.civico());
        indirizzo.setCitta(dto.citta());
        indirizzo.setProvincia(dto.provincia());
        indirizzo.setCap(dto.cap());
    }
}
