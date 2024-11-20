package traffic_id.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import traffic_id.demo.model.Moderator;
import traffic_id.demo.model.User;

public interface ModeratorRepository extends JpaRepository<Moderator, Integer> {

    @Query("SELECT m FROM Moderator m where m.user = ?1 and m.end_post = null")
    Optional<Moderator> findLastActive(User user);
}