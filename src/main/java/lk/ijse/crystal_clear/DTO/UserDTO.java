package lk.ijse.crystal_clear.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class UserDTO {

    private long userId;
    private String userName;
    private String userEmail;
    private String uPassword;
    private String userMobileNumber;
    private String userAddress;
    private String userWorkplace;
    private String userGender;
    private boolean  userprofileCompleted;
    private String userRole;



}
