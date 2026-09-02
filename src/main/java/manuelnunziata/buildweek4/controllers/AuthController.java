package manuelnunziata.buildweek4.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import manuelnunziata.buildweek4.entities.Utenti;
import manuelnunziata.buildweek4.payloads.LoginDTO;
import manuelnunziata.buildweek4.payloads.LoginResponseDTO;
import manuelnunziata.buildweek4.payloads.RegisterDTO;
import manuelnunziata.buildweek4.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Utenti register(@RequestBody @Valid RegisterDTO dto) {
        return authService.register(dto);
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody @Valid LoginDTO dto) {
        return authService.login(dto);
    }
}
