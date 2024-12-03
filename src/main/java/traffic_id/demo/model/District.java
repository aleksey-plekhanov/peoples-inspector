package traffic_id.demo.model;

import java.util.LinkedHashSet;
import java.util.Set;

import jakarta.persistence.*;

@Entity
@Table(name = "district")
public class District {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_district;

    @Column(name="Район", nullable = false, columnDefinition="VARCHAR(50)", unique=true)
    private String district;

    @OneToMany(mappedBy = "district")
    private Set<Application> applications = new LinkedHashSet<>();

    public District(Integer id_district, String district) {
        this.id_district = id_district;
        this.district = district;
    }

    public District() {
    }

    public Integer getId_district() {
        return id_district;
    }

    public void setId_district(Integer id_district) {
        this.id_district = id_district;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Set<Application> getApplications() {
        return applications;
    }

    public void setApplications(Set<Application> applications) {
        this.applications = applications;
    }

    @Override
    public String toString() {
        return "District [id_district=" + id_district + ", district=" + district + ", applications=" + applications
                + "]";
    }
}
