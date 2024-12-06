package traffic_id.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import traffic_id.demo.model.ApplicationData;

public interface ApplicationDataRepository extends JpaRepository<ApplicationData, Integer> {

    @Procedure("application_data_create")
    Integer createApplicationData(String information);

    @Procedure("application_data_fill")
    void fillApplicationData(int id_app_data, String[] audioPath, String[] photoPath , String[] videoPath , Integer[] violations);
}
