package traffic_id.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.lang.NonNull;

import traffic_id.demo.model.File;
import traffic_id.demo.model.User;

public interface FileRepository extends JpaRepository<File, Integer> {

    @Procedure("add_file")
    void addFile(Integer id_data, String path, String type);

    @NonNull
    @Query("select f " +
            "from File f inner join f.type t " +
            "where t.typeName = :typeName")
    List<File> findByTypeName(String typeName);
}
