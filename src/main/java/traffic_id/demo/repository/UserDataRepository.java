package traffic_id.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import traffic_id.demo.model.UserData;

public interface UserDataRepository extends JpaRepository<UserData, Integer> {

    UserData findByLogin(String login);

    UserData findByEmail(String email);
}