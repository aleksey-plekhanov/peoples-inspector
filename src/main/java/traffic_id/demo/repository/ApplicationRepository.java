package traffic_id.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import traffic_id.demo.model.Application;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {

    @Procedure("application_create")
    void createApplication(Integer id_user, String title, Integer id_data, String district, String address, String status);

    @Procedure("application_moderate")
    void moderateApplication(Integer id_app, Integer moderator, String commentary, String status);
}
