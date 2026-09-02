package manuelnunziata.buildweek4.repositories;

import manuelnunziata.buildweek4.entities.Utenti;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtentiRepository extends JpaRepository<Utenti, Long> {
    Optional<Utenti> findByEmail(String email);

    boolean existsByEmail(String email);
}