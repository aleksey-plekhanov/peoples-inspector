package traffic_id.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import traffic_id.demo.model.FileType;

public interface FileTypeRepository extends JpaRepository<FileType, String> {

}
