package manuelnunziata.buildweek4.services;

import lombok.RequiredArgsConstructor;
import manuelnunziata.buildweek4.entities.Utenti;
import manuelnunziata.buildweek4.exceptions.BadRequestException;
import manuelnunziata.buildweek4.exceptions.UnauthorizedException;
import manuelnunziata.buildweek4.payloads.LoginDTO;
import manuelnunziata.buildweek4.payloads.LoginResponseDTO;
import manuelnunziata.buildweek4.payloads.RegisterDTO;
import manuelnunziata.buildweek4.repositories.UtentiRepository;
import manuelnunziata.buildweek4.security.JwtTools;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UtentiRepository utentiRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTools jwtTools;

    public Utenti register(RegisterDTO dto) {
        if (utentiRepository.existsByEmail(dto.email())) {
            throw new BadRequestException("L'email " + dto.email() + " è già registrata");
        }

        Utenti utente = new Utenti(dto.email(), passwordEncoder.encode(dto.password()), dto.nome(), dto.cognome());
        return utentiRepository.save(utente);
    }

    public LoginResponseDTO login(LoginDTO dto) {
        Utenti utente = utentiRepository.findByEmail(dto.email())
                .orElseThrow(() -> new UnauthorizedException("Credenziali non valide"));

        if (!passwordEncoder.matches(dto.password(), utente.getPassword())) {
            throw new UnauthorizedException("Credenziali non valide");
        }

        return new LoginResponseDTO(jwtTools.createToken(utente));
    }
}
