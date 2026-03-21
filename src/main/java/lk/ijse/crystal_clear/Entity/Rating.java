package lk.ijse.crystal_clear.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor


public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rId;

    private Integer rRating;
    private String rComment;
    private LocalDateTime rDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;




}
