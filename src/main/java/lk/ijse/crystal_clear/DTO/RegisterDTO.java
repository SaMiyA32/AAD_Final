package lk.ijse.crystal_clear.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {
    private String userName;
    private String userEmail;
    private String userPassword;
    private boolean userprofileCompleted;
}
