package traffic_id.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import traffic_id.demo.model.Violation;

public interface ViolationRepository extends JpaRepository<Violation, Integer> {

}