package manuelnunziata.buildweek4.services;

import lombok.RequiredArgsConstructor;
import manuelnunziata.buildweek4.entities.Indirizzo;
import manuelnunziata.buildweek4.payloads.IndirizzoDTO;
import manuelnunziata.buildweek4.repositories.IndirizzoRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IndirizzoService {

    private final IndirizzoRepository indirizzoRepository;

    public Indirizzo create(IndirizzoDTO dto) {
        Indirizzo indirizzo = new Indirizzo();
        applyDto(indirizzo, dto);
        return indirizzoRepository.save(indirizzo);
    }

    private void applyDto(Indirizzo indirizzo, IndirizzoDTO dto) {
        indirizzo.setVia(dto.via());
        indirizzo.setCivico(dto.civico());
        indirizzo.setCitta(dto.citta());
        indirizzo.setProvincia(dto.provincia());
        indirizzo.setCap(dto.cap());
    }
}
