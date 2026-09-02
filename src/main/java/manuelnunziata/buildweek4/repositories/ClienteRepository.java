package manuelnunziata.buildweek4.repositories;

import manuelnunziata.buildweek4.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    boolean existsByIndirizzoId(Long indirizzoId);

    List<Cliente> findByRagioneSocialeContainingIgnoreCaseOrPartitaIvaContainingIgnoreCase(
            String ragioneSociale, String partitaIva);

    List<Cliente> findByCommercialeId(Long commercialeId);
}