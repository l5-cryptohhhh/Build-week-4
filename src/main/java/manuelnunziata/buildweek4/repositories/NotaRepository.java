package manuelnunziata.buildweek4.repositories;

import manuelnunziata.buildweek4.entities.Nota;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotaRepository extends JpaRepository<Nota, Long> {
    boolean existsByClienteId(Long clienteId);
    List<Nota> findByClienteId(Long clienteId);
}