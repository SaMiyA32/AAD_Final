package lk.ijse.crystal_clear.Service.custom;

import lk.ijse.crystal_clear.DTO.RatingDTO;
import java.util.List;

public interface RatingService {
    boolean saveRating(RatingDTO ratingDTO);
    List<RatingDTO> getAllRatings();
}
