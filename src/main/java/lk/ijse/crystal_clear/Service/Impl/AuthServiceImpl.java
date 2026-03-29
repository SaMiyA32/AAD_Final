package lk.ijse.crystal_clear.Service.Impl;

import lk.ijse.crystal_clear.DTO.AuthDTO;
import lk.ijse.crystal_clear.DTO.AuthResponseDTO;
import lk.ijse.crystal_clear.DTO.RegisterDTO;
import lk.ijse.crystal_clear.DTO.UserDTO;
import lk.ijse.crystal_clear.Entity.User;
import lk.ijse.crystal_clear.Exception.CustomException;
import lk.ijse.crystal_clear.Repo.UserRepo;
import lk.ijse.crystal_clear.Service.custom.AuthService;
import lk.ijse.crystal_clear.Util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;

    @Override
    public boolean register(RegisterDTO registerDTO) {
        if (userRepo.findByUserEmail(registerDTO.getUserEmail()).isPresent()) {
            throw new CustomException("Email is already in use", 409);
        }
        User user = modelMapper.map(registerDTO, User.class);
        user.setUserPassword(passwordEncoder.encode(registerDTO.getUserPassword()));
        
        if ("spasan42@gmail.com".equalsIgnoreCase(registerDTO.getUserEmail()) || "admin@gmail.com".equalsIgnoreCase(registerDTO.getUserEmail())) {
            user.setUserRole(lk.ijse.crystal_clear.Entity.Role.ADMIN);
        } else if (user.getUserRole() == null) {
            user.setUserRole(lk.ijse.crystal_clear.Entity.Role.USER);
        }
        
        userRepo.save(user);
        return true;
    }

    @Override
    public AuthResponseDTO authenticate(AuthDTO authDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authDTO.getUserEmail(), authDTO.getUserPassword())
        );

        User user = userRepo.findByUserEmail(authDTO.getUserEmail())
                .orElseThrow(() -> new CustomException("User not found", 404));

        String token = jwtUtil.generateToken(user.getUserEmail());
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        userDTO.setUserName(user.getUserName());

        return new AuthResponseDTO(token, userDTO);
    }
}
