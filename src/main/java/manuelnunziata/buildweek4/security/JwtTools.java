package manuelnunziata.buildweek4.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import manuelnunziata.buildweek4.entities.Utenti;
import manuelnunziata.buildweek4.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class JwtTools {

    @Value("${jwt.secret}")
    private String secret;

    public String createToken(Utenti utente) {
        return Jwts.builder()
                .subject(String.valueOf(utente.getId()))
                .issuedAt(new java.util.Date())
                .expiration(java.util.Date.from(Instant.now().plus(1, ChronoUnit.DAYS)))
                .signWith(getSigningKey())
                .compact();
    }

    public String validateToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        } catch (Exception e) {
            throw new UnauthorizedException("Token non valido o scaduto");
        }
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
}
