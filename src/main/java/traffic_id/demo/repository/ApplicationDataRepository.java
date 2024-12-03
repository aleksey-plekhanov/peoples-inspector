package traffic_id.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import traffic_id.demo.model.ApplicationData;

public interface ApplicationDataRepository extends JpaRepository<ApplicationData, Integer> {

}
