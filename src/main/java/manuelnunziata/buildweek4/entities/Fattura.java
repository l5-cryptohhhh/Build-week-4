package manuelnunziata.buildweek4.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "fatture")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Fattura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;
    @Column(nullable = false, unique = true)
    private String numero;
    @Column(nullable = false)
    private int importo;
    @Column(nullable = false)
    private LocalDate scadenza;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatoFattura stato = StatoFattura.BOZZA;
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
    @CreationTimestamp
    private LocalDateTime dataCreazione;
    @UpdateTimestamp
    private LocalDateTime dataUltimaModifica;

    public Fattura(String numero, int importo, LocalDate scadenza, StatoFattura stato, Cliente cliente, LocalDateTime dataCreazione, LocalDateTime dataUltimaModifica) {
        this.numero = numero;
        this.importo = importo;
        this.scadenza = scadenza;
        this.stato = stato;
        this.cliente = cliente;
        this.dataCreazione = dataCreazione;
        this.dataUltimaModifica = dataUltimaModifica;
    }
}
