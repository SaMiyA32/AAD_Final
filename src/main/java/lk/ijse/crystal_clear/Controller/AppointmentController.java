package lk.ijse.crystal_clear.Controller;

import lk.ijse.crystal_clear.DTO.AppointmentDTO;
import lk.ijse.crystal_clear.Service.custom.AppointmentService;
import lk.ijse.crystal_clear.Util.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

     @PostMapping
    public ResponseEntity<APIResponse> saveAppointment(@RequestBody AppointmentDTO dto) {
        boolean isSaved = appointmentService.saveAppointment(dto);
        APIResponse response = new APIResponse(201, "Appointment Booked Successfully!", isSaved);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

     @GetMapping("/user/{userId}")
    public ResponseEntity<APIResponse> getUserAppointments(@PathVariable("userId") Long userId) {
        List<AppointmentDTO> list = appointmentService.getUserAppointments(userId);
        APIResponse response = new APIResponse(200, "User Appointments Loaded", list);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

     @GetMapping
    public ResponseEntity<APIResponse> getAllAppointments() {
        List<AppointmentDTO> list = appointmentService.getAllAppointments();
        APIResponse response = new APIResponse(200, "All Appointments Loaded", list);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

     @PutMapping("/{id}/accept")
    public ResponseEntity<APIResponse> acceptAppointment(@PathVariable("id") Long id) {
        boolean isAccepted = appointmentService.acceptAppointment(id);
        APIResponse response = new APIResponse(200, "Appointment Accepted!", isAccepted);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

     @PutMapping("/{id}/cancel")
    public ResponseEntity<APIResponse> cancelAppointment(@PathVariable("id") Long id, @RequestBody Map<String, String> body) {
        String cancelReason = body.get("aCancelReason");
        boolean isCancelled = appointmentService.cancelAppointment(id, cancelReason);
        APIResponse response = new APIResponse(200, "Appointment Cancelled!", isCancelled);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
