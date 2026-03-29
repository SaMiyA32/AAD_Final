package lk.ijse.crystal_clear.Service.custom;

import java.time.LocalDate;
import java.time.LocalTime;

public interface EmailService {
    void sendAppointmentAcceptedEmail(String to, String userName, LocalDate date, LocalTime time, String address);
    void sendAppointmentCanceledEmail(String to, String userName, String cancelReason);
}
