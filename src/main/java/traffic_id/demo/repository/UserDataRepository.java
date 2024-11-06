package traffic_id.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import traffic_id.demo.model.UserData;

public interface UserDataRepository extends JpaRepository<UserData, Integer> {

}