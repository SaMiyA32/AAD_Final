package lk.ijse.crystal_clear.Service.custom;

import lk.ijse.crystal_clear.DTO.AuthDTO;
import lk.ijse.crystal_clear.DTO.AuthResponseDTO;
import lk.ijse.crystal_clear.DTO.RegisterDTO;

public interface AuthService {
    boolean register(RegisterDTO registerDTO);
    AuthResponseDTO authenticate(AuthDTO authDTO);
}
