package lk.ijse.crystal_clear.Service.custom;

import lk.ijse.crystal_clear.DTO.UserDTO;

public interface UserService {

    boolean saveUser(UserDTO userDTO);
    UserDTO verifyUser(String email, String pwd);
    UserDTO getUserById(Long userId);
    UserDTO updateUser(UserDTO userDTO);
}
