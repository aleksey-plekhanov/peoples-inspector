package traffic_id.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import traffic_id.demo.model.Status;

public interface StatusRepository extends JpaRepository<Status, Integer> {

    Status findByStatus(String status);
}