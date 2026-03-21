package lk.ijse.crystal_clear.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class RatingDTO {

    private Long rId;
    private Integer rRating;
    private String rComment;
    private LocalDateTime rDate;

    private Long aptId;
    private Long userId;
    private String userName;
    private String serviceType;



}
