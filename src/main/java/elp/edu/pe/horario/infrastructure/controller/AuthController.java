package elp.edu.pe.horario.infrastructure.controller;

import elp.edu.pe.horario.application.dto.request.AuthRequest;
import elp.edu.pe.horario.application.dto.response.AuthResponse;
import elp.edu.pe.horario.application.dto.response.RegisterResponse;
import elp.edu.pe.horario.application.usecase.user.UserLoginUseCase;
import elp.edu.pe.horario.application.usecase.user.UserRegisterUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserLoginUseCase userLoginUseCase;
    private final UserRegisterUseCase userRegisterUseCase;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        AuthResponse response = userLoginUseCase.execute(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody AuthRequest request) {
        RegisterResponse response = userRegisterUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
