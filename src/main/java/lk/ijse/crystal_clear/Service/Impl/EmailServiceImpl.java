package lk.ijse.crystal_clear.Service.Impl;

import lk.ijse.crystal_clear.Service.custom.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;


    private final String SENDER_EMAIL = "samiyakolla40@gmail.com";

    @Async
    @Override
    public void sendAppointmentAcceptedEmail(String to, String userName, LocalDate date, LocalTime time, String address) {
        String formattedDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String formattedTime = time.format(DateTimeFormatter.ofPattern("hh:mm a"));

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(SENDER_EMAIL);
        message.setTo(to);
        message.setSubject("Appointment Accepted - Crystal Clear Cleaning Services");
        
        String text = String.format(
            "Dear %s,\n\n" +
            "Great news! Your cleaning service appointment has been accepted.\n\n" +
            "Appointment Details:\n" +
            "- Date: %s\n" +
            "- Time: %s\n" +
            "- Location: %s\n\n" +
            "Our cleaning team will arrive at the scheduled time. Thank you for choosing Crystal Clear!\n\n" +
            "Best Regards,\n" +
            "The Crystal Clear Team",
            userName, formattedDate, formattedTime, address
        );
        
        message.setText(text);
        
        System.out.println(">>> STARTING TO SEND ACCEPTANCE EMAIL TO: " + to + " FROM: " + SENDER_EMAIL + " <<<");
        try {
            javaMailSender.send(message);
            System.out.println("Acceptance email sent successfully to " + to);
        } catch (Exception e) {
            System.err.println("Error sending acceptance email. Appointment was saved, but email failed: " + e.getMessage());
        }
    }

    @Async
    @Override
    public void sendAppointmentCanceledEmail(String to, String userName, String cancelReason) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(SENDER_EMAIL);
        message.setTo(to);
        message.setSubject("Appointment Canceled - Crystal Clear Cleaning Services");
        
        String text = String.format(
            "Dear %s,\n\n" +
            "We regret to inform you that your cleaning service appointment has been canceled.\n\n" +
            "Reason for cancellation: %s\n\n" +
            "If you have any questions or would like to reschedule, please contact our support team.\n\n" +
            "Best Regards,\n" +
            "The Crystal Clear Team",
            userName, cancelReason
        );
        
        message.setText(text);
        
        System.out.println(">>> STARTING TO SEND CANCELLATION EMAIL TO: " + to + " FROM: " + SENDER_EMAIL + " <<<");
        try {
            javaMailSender.send(message);
            System.out.println("Cancellation email sent successfully to " + to);
        } catch (Exception e) {
            System.err.println("Error sending cancellation email. Appointment was canceled, but email failed: " + e.getMessage());
        }
    }
}
