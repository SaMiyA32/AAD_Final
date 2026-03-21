package lk.ijse.crystal_clear.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    private String userName;

    @Column(unique = true)
    private String userEmail;

    private String userPassword;
    private String userMobileNumber;
    private String userAddress;
    private String userWorkplace;
    private String userGender;
    private boolean  userprofileCompleted;
    private String userRole;




}
