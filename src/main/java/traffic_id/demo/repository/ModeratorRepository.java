package traffic_id.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import traffic_id.demo.model.Moderator;
import traffic_id.demo.model.User;

public interface ModeratorRepository extends JpaRepository<Moderator, Integer> {

    // @Query(value = "select * from public.moderator " +
    //             "where \"Пользователь\" = ?1 " +
    //             "and \"Дата окончания должности\" = null", nativeQuery = true)
    @Query("SELECT m FROM Moderator m where m.user = ?1 and m.endPost IS NULL")
    Moderator findLastActive(User user);
}