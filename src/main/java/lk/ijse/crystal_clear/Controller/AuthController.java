package lk.ijse.crystal_clear.Controller;

import lk.ijse.crystal_clear.DTO.AuthDTO;
import lk.ijse.crystal_clear.DTO.RegisterDTO;
import lk.ijse.crystal_clear.Service.custom.AuthService;
import lk.ijse.crystal_clear.Util.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<APIResponse> registerUser(@RequestBody RegisterDTO registerDTO) {
        return ResponseEntity.ok(new APIResponse(201, "Registration Successful!", authService.register(registerDTO)));
    }

    @PostMapping("/login")
    public ResponseEntity<APIResponse> loginUser(@RequestBody AuthDTO authDTO) {
        return ResponseEntity.ok(new APIResponse(200, "Login Successful!", authService.authenticate(authDTO)));
    }
}
