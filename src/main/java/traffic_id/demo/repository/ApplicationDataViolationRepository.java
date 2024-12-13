package traffic_id.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import traffic_id.demo.model.ApplicationDataViolation;

public interface ApplicationDataViolationRepository extends JpaRepository<ApplicationDataViolation, Integer> {

    @Procedure("application_data_add_violations")
    void addViolations(Integer id_app_data, String[] violations);
}
