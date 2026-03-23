package lk.ijse.crystal_clear.Service.Impl;

import lk.ijse.crystal_clear.DTO.AppointmentDTO;
import lk.ijse.crystal_clear.Entity.Appointment;
import lk.ijse.crystal_clear.Entity.User;
import lk.ijse.crystal_clear.Exception.CustomException;
import lk.ijse.crystal_clear.Repo.AppointmentRepo;
import lk.ijse.crystal_clear.Repo.UserRepo;
import lk.ijse.crystal_clear.Service.custom.AppointmentService;
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
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepo appointmentRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public boolean saveAppointment(AppointmentDTO dto) {
        Optional<User> userOptional = userRepo.findById(dto.getUserId());

        if (userOptional.isPresent()) {
            Appointment appointment = modelMapper.map(dto, Appointment.class);
            appointment.setUser(userOptional.get());
            appointment.setACreatedAt(LocalDateTime.now());

            appointmentRepo.save(appointment);
            return true;
        } else {
            throw new CustomException("Booking Failed: User not found!", 404);
        }
    }

    @Override
    public List<AppointmentDTO> getUserAppointments(Long userId) {
        List<Appointment> userAppointments = appointmentRepo.findByUser_UserId(userId);

        return modelMapper.map(userAppointments, new TypeToken<List<AppointmentDTO>>() {}.getType());
    }

    @Override
    public List<AppointmentDTO> getAllAppointments() {
        List<Appointment> allAppointments = appointmentRepo.findAll();
        return modelMapper.map(allAppointments, new TypeToken<List<AppointmentDTO>>() {}.getType());
    }

    @Override
    public boolean acceptAppointment(Long aptId) {
        Optional<Appointment> optionalAppointment = appointmentRepo.findById(aptId);

        if (optionalAppointment.isPresent()) {
            Appointment appointment = optionalAppointment.get();
            appointment.setAStatus("Accepted");
            appointmentRepo.save(appointment);
            return true;
        } else {
            throw new CustomException("Cannot accept: Appointment not found!", 404);
        }
    }

    @Override
    public boolean cancelAppointment(Long aptId, String cancelReason) {
        Optional<Appointment> optionalAppointment = appointmentRepo.findById(aptId);

        if (optionalAppointment.isPresent()) {
            Appointment appointment = optionalAppointment.get();
            appointment.setAStatus("Cancelled");
            appointment.setACancelReason(cancelReason);
            appointmentRepo.save(appointment);
            return true;
        } else {
            throw new CustomException("Cannot cancel: Appointment not found!", 404);
        }
    }
}
