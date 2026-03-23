package lk.ijse.crystal_clear.Controller;

import lk.ijse.crystal_clear.DTO.UserDTO;
import lk.ijse.crystal_clear.Service.custom.UserService;
import lk.ijse.crystal_clear.Util.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/auth/register")
    public ResponseEntity<APIResponse> registerUser(@RequestBody UserDTO dto) {
        boolean isSaved = userService.saveUser(dto);
        APIResponse response = new APIResponse(201, "Registration Successful!", isSaved);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @PostMapping("/auth/login")
    public ResponseEntity<APIResponse> loginUser(@RequestBody UserDTO loginDto) {
        UserDTO verifiedUser = userService.verifyUser(loginDto.getUserEmail(), loginDto.getUserPassword());
        APIResponse response = new APIResponse(200, "Login Successful!", verifiedUser);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PutMapping("/users/{id}")
    public ResponseEntity<APIResponse> updateUser(@PathVariable Long id, @RequestBody UserDTO dto) {
        dto.setUserId(id);
        UserDTO updatedUser = userService.updateUser(dto);
        APIResponse response = new APIResponse(200, "Profile Updated!", updatedUser);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
