package lk.ijse.crystal_clear.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class AppointmentDTO {

    private Long aId;
    private String aService;
    private LocalDate aDate;
    private LocalTime aTime;
    private String aAddress;
    private String aNote;
    private String aStatus;
    private String aCancelReason;
    private LocalDateTime aCreatedAt;

    private Long userId;
    private String userName;
    private String userPhone;


}
