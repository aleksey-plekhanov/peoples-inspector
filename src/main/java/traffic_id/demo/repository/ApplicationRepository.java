package traffic_id.demo.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.jpa.repository.Query;

import traffic_id.demo.model.Application;
import traffic_id.demo.model.User;
import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {

    @Procedure("application_create")
    Integer createApplication(Integer id_user, String title, String information, Date dateViolation, String district, String address, String status);

    @Procedure("application_moderate")
    void moderateApplication(Integer id_app, Integer moderator, String commentary, String status);

    @Procedure("application_get_data")
    void getApplicationData(Integer id_app, String[] filePath, String[] violations);

    List<Application> findByUser(User user);

    @Query(value = "select * from public.application " +
                "where \"Статус\" = 'На рассмотрении' " +
                "and \"Заявитель\" <> ?1", nativeQuery = true)
    List<Application> findUncheckedApplications(Integer modertorId);
}
