package traffic_id.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import traffic_id.demo.model.District;

public interface DistrictRepository extends JpaRepository<District, String> {
    
    District findByDistrictName(String districtName);
}
