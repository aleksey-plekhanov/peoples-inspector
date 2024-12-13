package traffic_id.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import traffic_id.demo.model.ApplicationData;
import java.util.List;
import java.util.Optional;


public interface ApplicationDataRepository extends JpaRepository<ApplicationData, Integer> {

    @Procedure("application_data_create")
    Integer createApplicationData(String information);

    @Procedure("application_get_data")
    void getApplicationData(Integer id_app_data, String information, String[] audioPath, String[] photoPath, String[] videoPath, String[] violations);

    ApplicationData findByApplicationDataId(Integer id); 
}
