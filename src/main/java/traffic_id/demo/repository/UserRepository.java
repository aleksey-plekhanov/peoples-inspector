package traffic_id.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import traffic_id.demo.model.User;
import traffic_id.demo.model.UserData;

public interface UserRepository extends JpaRepository<User, Integer> {
    
    Optional<User> findByData(UserData data);

    @Override
    @NonNull
    @Query("select u.id_user, u.surname, u.name, u.patronymic, d.email, u.registration " +
            "from User u inner join u.data d " +
            "where d.id_user_data = :id_user_data")
    List<User> findAll();
}