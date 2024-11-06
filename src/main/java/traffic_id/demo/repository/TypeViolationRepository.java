package traffic_id.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import traffic_id.demo.model.TypeViolation;

public interface TypeViolationRepository extends JpaRepository<TypeViolation, Integer> {

}
