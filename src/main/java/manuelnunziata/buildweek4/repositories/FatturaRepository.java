package manuelnunziata.buildweek4.repositories;

import manuelnunziata.buildweek4.entities.Fattura;
import manuelnunziata.buildweek4.entities.StatoFattura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FatturaRepository extends JpaRepository<Fattura, Long> {
    boolean existsByClienteId(Long clienteId);

    List<Fattura> findByClienteId(Long clienteId);

    List<Fattura> findByStato(StatoFattura stato);
}
