package lk.ijse.crystal_clear.Repo;

import lk.ijse.crystal_clear.Entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, Long> {


    List<Appointment> findByUser_UserId(Long userId);

}
