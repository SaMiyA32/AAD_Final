package lk.ijse.crystal_clear.Entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aId;

    private String aService;
    private LocalDate aDate;
    private LocalTime aTime;
    private String aAddress;
    private String aNote;
    private String aStatus;
    private String aCancelReason;
    private LocalDateTime aCreatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
