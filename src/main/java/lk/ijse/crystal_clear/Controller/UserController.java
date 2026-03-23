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





    @PutMapping("/users/{id}")
    public ResponseEntity<APIResponse> updateUser(@PathVariable("id") Long id, @RequestBody UserDTO dto) {
        dto.setUserId(id);
        UserDTO updatedUser = userService.updateUser(dto);
        APIResponse response = new APIResponse(200, "Profile Updated!", updatedUser);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
