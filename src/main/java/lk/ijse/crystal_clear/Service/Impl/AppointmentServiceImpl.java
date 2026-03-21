package lk.ijse.crystal_clear.Service.Impl;

import lk.ijse.crystal_clear.DTO.AppointmentDTO;
import lk.ijse.crystal_clear.Entity.Appointment;
import lk.ijse.crystal_clear.Repo.AppointmentRepo;
import lk.ijse.crystal_clear.Service.custom.AppointmentService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepo appointmentRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public boolean saveAppointment(AppointmentDTO appointmentDTO) {
        Appointment appointment = modelMapper.map(appointmentDTO, Appointment.class);
        appointmentRepo.save(appointment);
        return true;
    }

    @Override
    public List<AppointmentDTO> getUserAppointments(Long userId) {
        return null;
    }

    @Override
    public List<AppointmentDTO> getAllAppointments() {
        List<Appointment> allAppointments = appointmentRepo.findAll();
        return modelMapper.map(allAppointments, new TypeToken<List<AppointmentDTO>>() {
        }.getType());
    }

    @Override
    public boolean acceptAppointment(Long aptId) {
        Optional<Appointment> optionalAppointment = appointmentRepo.findById(aptId);
        if (optionalAppointment.isPresent()) {
            Appointment appointment = optionalAppointment.get();
            appointment.setAStatus("Accepted");
            appointmentRepo.save(appointment);
            return true;
        }
        return false;
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
        }
        return false;
    }
}
