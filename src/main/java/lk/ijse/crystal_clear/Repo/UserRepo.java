package lk.ijse.crystal_clear.Repo;

import lk.ijse.crystal_clear.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByUserEmail(String userEmail);

}
