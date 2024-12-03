package traffic_id.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import traffic_id.demo.model.ApplicationDataViolation;

public interface ApplicationDataViolationRepository extends JpaRepository<ApplicationDataViolation, Integer> {

}
