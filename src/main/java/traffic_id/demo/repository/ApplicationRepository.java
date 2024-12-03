package traffic_id.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import traffic_id.demo.model.Application;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {

}
