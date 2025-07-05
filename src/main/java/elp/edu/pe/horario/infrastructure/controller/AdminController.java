package elp.edu.pe.horario.infrastructure.controller;

import elp.edu.pe.horario.application.dto.request.AuthRequest;
import elp.edu.pe.horario.application.dto.request.UpdateUserRequest;
import elp.edu.pe.horario.application.dto.response.RegisterResponse;
import elp.edu.pe.horario.application.dto.response.RegistroResponse;
import elp.edu.pe.horario.application.usecase.user.FindUsersUseCase;
import elp.edu.pe.horario.application.usecase.user.UserRegisterUseCase;
import elp.edu.pe.horario.application.usecase.user.UserUpdateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserUpdateUseCase userUpdateUseCase;
    private final UserRegisterUseCase userRegisterUseCase;
    private final FindUsersUseCase findUsersUseCase;

    @PutMapping
    public ResponseEntity<RegistroResponse> updateUser(@RequestBody UpdateUserRequest request ) {
        var response = userUpdateUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody AuthRequest request) {
        RegisterResponse response = userRegisterUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/users")
    public ResponseEntity<?> findAllUsers() {
        return ResponseEntity.ok(findUsersUseCase.findAll());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> findUserById(@PathVariable Long id) {
        return ResponseEntity.ok(findUsersUseCase.findById(id));
    }





}
