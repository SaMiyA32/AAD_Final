package lk.ijse.crystal_clear.Controller;

import lk.ijse.crystal_clear.DTO.RatingDTO;
import lk.ijse.crystal_clear.Service.custom.RatingService;
import lk.ijse.crystal_clear.Util.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
@CrossOrigin
public class RatingController {

    @Autowired
    private RatingService ratingService;

     @PostMapping
    public ResponseEntity<APIResponse> saveRating(@RequestBody RatingDTO dto) {
        boolean isSaved = ratingService.saveRating(dto);
        APIResponse response = new APIResponse(201, "Rating Saved Successfully!", isSaved);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

        @GetMapping
    public ResponseEntity<APIResponse> getAllRatings() {
        List<RatingDTO> list = ratingService.getAllRatings();
        APIResponse response = new APIResponse(200, "Ratings Loaded", list);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
