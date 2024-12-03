package traffic_id.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import traffic_id.demo.model.ViolationType;

public interface ViolationTypeRepository extends JpaRepository<ViolationType, Integer> {

}
