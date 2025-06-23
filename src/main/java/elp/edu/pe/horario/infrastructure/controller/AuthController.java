package elp.edu.pe.horario.infrastructure.controller;

import elp.edu.pe.horario.application.dto.request.AuthRequest;
import elp.edu.pe.horario.application.dto.response.AuthResponse;
import elp.edu.pe.horario.application.usecase.user.UserLoginUseCase;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        AuthResponse response = userLoginUseCase.execute(request);
        return ResponseEntity.ok(response);
    }

}
