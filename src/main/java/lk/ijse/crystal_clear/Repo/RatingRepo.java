package lk.ijse.crystal_clear.Repo;

import lk.ijse.crystal_clear.Entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepo extends JpaRepository<Rating, Long> {
}
