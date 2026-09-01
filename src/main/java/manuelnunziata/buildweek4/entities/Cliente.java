package manuelnunziata.buildweek4.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "clienti")
@Getter
@Setter
@Data
@NoArgsConstructor
@ToString


public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;
    @Column(nullable = false)
    private String ragioneSociale;
    @Column(unique = true, nullable = false)
    private String partitaIva;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String telefono;
    @ManyToOne
    @JoinColumn(name = "indirizzo_id", nullable = false)
    private Indirizzo indirizzo;
    @ManyToOne
    @JoinColumn(name = "commerciale_id", nullable = false)
    private Utenti commerciale;
    @CreationTimestamp
    private LocalDateTime dataCreazione;
    @UpdateTimestamp
    private LocalDateTime dataUltimaModifica;

    public Cliente(String ragioneSociale, String partitaIva, String email, String telefono, Indirizzo indirizzo, Utenti commerciale) {
        this.ragioneSociale = ragioneSociale;
        this.partitaIva = partitaIva;
        this.email = email;
        this.telefono = telefono;
        this.indirizzo = indirizzo;
        this.commerciale = commerciale;
    }
}
