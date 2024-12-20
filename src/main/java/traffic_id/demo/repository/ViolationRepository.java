package traffic_id.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import traffic_id.demo.model.Violation;

public interface ViolationRepository extends JpaRepository<Violation, String> {

    // @Query(value = "select * from public.violation " +
    //             "where upper(\"Статья\") LIKE CONCAT('%',upper(:article),'%') " + 
    //             "and upper(\"Название\") LIKE CONCAT('%',upper(:title),'%') " + 
    //             "and upper(\"Вид\") LIKE CONCAT('%',upper(:type_viol),'%') " + 
    //             "and upper(\"Наказание\") LIKE CONCAT('%',upper(:punishment),'%')", nativeQuery = true)
    // List<Violation> findViolation(String article, String title, String type_viol, String punishment);
}