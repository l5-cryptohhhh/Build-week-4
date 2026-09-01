package manuelnunziata.buildweek4.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "indirizzi")
@Getter
@Setter
@Data
@NoArgsConstructor
@ToString

public class Indirizzo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;
    @Column(unique = true, nullable = false)
    private String via;
    @Column(nullable = false)
    private String civico;
    @Column(nullable = false)
    private String citta;
    @Column(nullable = false)
    private String provincia;
    @Column(nullable = false)
    private String cap;
    @CreationTimestamp
    private LocalDateTime dataCreazione;
    @UpdateTimestamp
    private LocalDateTime dataUltimaModifica;

    public Indirizzo(String via, String civico, String citta, String provincia, String cap, LocalDateTime dataCreazione, LocalDateTime dataUltimaModifica) {
        this.via = via;
        this.civico = civico;
        this.citta = citta;
        this.provincia = provincia;
        this.cap = cap;
        this.dataCreazione = dataCreazione;
        this.dataUltimaModifica = dataUltimaModifica;
    }
}
