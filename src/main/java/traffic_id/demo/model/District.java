package traffic_id.demo.model;

import java.util.LinkedHashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;

@Entity
@Table(name = "district")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class District {

    @Id
    @Column(name="Район", nullable = false, columnDefinition="VARCHAR(30)", unique=true)
    private String districtName;

    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "district")
    private Set<Application> applications = new LinkedHashSet<>();

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

    public Set<Application> getApplications() {
        return applications;
    }

    public void setApplications(Set<Application> applications) {
        this.applications = applications;
    }

    @Override
    public String toString() {
        return "District [districtName=" + districtName + "]";
    }
}
