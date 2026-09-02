package manuelnunziata.buildweek4.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "note")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Nota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;
    @Column(nullable = false, length = 2000)
    private String testo;
    @ManyToOne
    @JoinColumn(name = "clienti_id", nullable = false)
    private Cliente cliente;
    @CreationTimestamp
    private LocalDateTime dataCreazione;

    public Nota(String testo, Cliente cliente, LocalDateTime dataCreazione) {
        this.testo = testo;
        this.cliente = cliente;
        this.dataCreazione = dataCreazione;
    }



}
