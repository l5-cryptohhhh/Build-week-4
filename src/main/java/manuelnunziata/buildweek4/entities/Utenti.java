package manuelnunziata.buildweek4.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "utenti")
@Getter
@Setter
@Data
@NoArgsConstructor
@ToString

public class Utenti {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    @JsonIgnore
    private String password;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String cognome;
    @Enumerated(EnumType.STRING)
    private Ruolo ruolo;

    public Utenti(String email, String password, String nome, String cognome) {
        this.email = email;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
    }
}
