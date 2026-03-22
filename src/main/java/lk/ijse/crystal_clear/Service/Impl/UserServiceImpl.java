package lk.ijse.crystal_clear.Service.Impl;

import lk.ijse.crystal_clear.DTO.UserDTO;
import lk.ijse.crystal_clear.Entity.User;
import lk.ijse.crystal_clear.Exception.CustomException;
import lk.ijse.crystal_clear.Repo.UserRepo;
import lk.ijse.crystal_clear.Service.custom.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public boolean saveUser(UserDTO userDTO) {
        Optional<User> existingUser = userRepo.findByUserEmail(userDTO.getUserEmail());
        if (existingUser.isPresent()) {
            throw new CustomException("This email is already registered!", 400);
        }

        User user = modelMapper.map(userDTO, User.class);
        userRepo.save(user);
        return true;
    }

    @Override
    public UserDTO verifyUser(String email, String pwd) {
        Optional<User> userOptional = userRepo.findByUserEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (user.getUserPassword().equals(pwd)) {
                return modelMapper.map(user, UserDTO.class);
            } else {
                 throw new CustomException("Incorrect password!", 401);
            }
        } else {

            throw new CustomException("User not found for this email!", 404);
        }
    }

    @Override
    public UserDTO getUserById(Long userId) {
        Optional<User> userOptional = userRepo.findById(userId);
        if (userOptional.isPresent()) {
            return modelMapper.map(userOptional.get(), UserDTO.class);
        } else {
            throw new CustomException("User ID not found!", 404);
        }
    }
}
