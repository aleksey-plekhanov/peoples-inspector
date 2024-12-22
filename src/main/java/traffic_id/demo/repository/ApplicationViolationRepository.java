package traffic_id.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import traffic_id.demo.model.ApplicationViolation;
import traffic_id.demo.model.Application;
import java.util.List;


public interface ApplicationViolationRepository extends JpaRepository<ApplicationViolation, Integer> {

    @Procedure("application_add_violations")
    void addViolations(Integer id_app, String[] violations);

    List<ApplicationViolation> findByApplication(Application application);
}
