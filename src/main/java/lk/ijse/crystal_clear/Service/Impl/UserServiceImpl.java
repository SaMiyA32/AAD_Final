package lk.ijse.crystal_clear.Service.Impl;

import lk.ijse.crystal_clear.DTO.UserDTO;
import lk.ijse.crystal_clear.Entity.User;
import lk.ijse.crystal_clear.Repo.UserRepo;
import lk.ijse.crystal_clear.Service.custom.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public boolean saveUser(UserDTO userDTO) {

        User user = modelMapper.map(userDTO, User.class);

        userRepo.save(user);
        return true;
    }

    @Override
    public UserDTO verifyUser(String email, String pwd) {
        return null;
    }

    @Override
    public UserDTO getUserById(Long userId) {
        return null;
    }
}
