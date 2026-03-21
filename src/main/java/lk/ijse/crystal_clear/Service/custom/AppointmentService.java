package lk.ijse.crystal_clear.Service.custom;

import lk.ijse.crystal_clear.DTO.AppointmentDTO;
import java.util.List;

public interface AppointmentService {
    boolean saveAppointment(AppointmentDTO appointmentDTO);
    List<AppointmentDTO> getUserAppointments(Long userId);
    List<AppointmentDTO> getAllAppointments();
    boolean acceptAppointment(Long aptId);
    boolean cancelAppointment(Long aptId, String cancelReason);
}
