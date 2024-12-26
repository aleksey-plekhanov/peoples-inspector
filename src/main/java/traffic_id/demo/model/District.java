package traffic_id.demo.model;

import java.util.LinkedHashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;

@Entity
@Table(name = "district")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "districtName")
public class District {

    @Id
    @Column(name="Район", nullable = false, columnDefinition="VARCHAR(30)", unique=true)
    private String districtName;

    public District(String districtName) {
        this.districtName = districtName;
    }

    public District() {
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    @Override
    public String toString() {
        return "District [districtName=" + districtName + "]";
    }
}
