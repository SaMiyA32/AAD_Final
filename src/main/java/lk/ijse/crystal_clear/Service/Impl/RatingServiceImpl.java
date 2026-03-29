package lk.ijse.crystal_clear.Service.Impl;

import lk.ijse.crystal_clear.DTO.RatingDTO;
import lk.ijse.crystal_clear.Entity.Appointment;
import lk.ijse.crystal_clear.Entity.Rating;
import lk.ijse.crystal_clear.Entity.User;
import lk.ijse.crystal_clear.Exception.CustomException;
import lk.ijse.crystal_clear.Repo.AppointmentRepo;
import lk.ijse.crystal_clear.Repo.RatingRepo;
import lk.ijse.crystal_clear.Repo.UserRepo;
import lk.ijse.crystal_clear.Service.custom.RatingService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepo ratingRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AppointmentRepo appointmentRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public boolean saveRating(RatingDTO dto) {
        Optional<User> userOptional = userRepo.findById(dto.getUserId());
        Optional<Appointment> appointmentOptional = appointmentRepo.findById(dto.getAptId());

        if (userOptional.isPresent() && appointmentOptional.isPresent()) {
            Rating rating = modelMapper.map(dto, Rating.class);
            rating.setUser(userOptional.get());
            rating.setAppointment(appointmentOptional.get());
            rating.setRDate(LocalDateTime.now());

            ratingRepo.save(rating);
            return true;
        } else {
            throw new CustomException("Failed to save rating: User or Appointment not found!", 404);
        }
    }

    @Override
    public List<RatingDTO> getAllRatings() {
        List<Rating> allRatings = ratingRepo.findAll();
        List<RatingDTO> dtos = modelMapper.map(allRatings, new TypeToken<List<RatingDTO>>() {}.getType());
        for (int i = 0; i < allRatings.size(); i++) {
            Rating r = allRatings.get(i);
            if (r.getUser() != null) {
                dtos.get(i).setUserId(r.getUser().getUserId());
                dtos.get(i).setUserName(r.getUser().getUserName());
            }
            if (r.getAppointment() != null) {
                dtos.get(i).setAptId(r.getAppointment().getAId());
                dtos.get(i).setServiceType(r.getAppointment().getAService());
            }
        }
        return dtos;
    }
}
