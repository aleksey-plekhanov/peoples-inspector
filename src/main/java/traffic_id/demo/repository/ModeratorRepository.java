package traffic_id.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import traffic_id.demo.model.Moderator;

public interface ModeratorRepository extends JpaRepository<Moderator, Integer> {

}