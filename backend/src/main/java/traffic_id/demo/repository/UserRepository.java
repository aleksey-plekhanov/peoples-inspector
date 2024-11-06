package traffic_id.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import traffic_id.demo.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}