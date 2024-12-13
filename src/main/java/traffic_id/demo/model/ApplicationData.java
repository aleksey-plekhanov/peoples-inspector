package traffic_id.demo.model;

import java.util.LinkedHashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;

@Entity
@Table(name = "application_data")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class ApplicationData {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer applicationDataId;
     
    @Column(name="Информация", nullable = false, columnDefinition="text")
    private String information;

    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "applicationData")
    private Set<File> files = new LinkedHashSet<>();

    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "applicationData")
    private Set<ApplicationDataViolation> violations = new LinkedHashSet<>();

    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "applicationData")
    private Set<Application> applications = new LinkedHashSet<>();

    public ApplicationData(Integer id_application_data, String information) {
        this.applicationDataId = id_application_data;
        this.information = information;
    }

    public ApplicationData() {
    }

    public Integer getId() {
        return applicationDataId;
    }

    public void setId(Integer id_application_data) {
        this.applicationDataId = id_application_data;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public Set<File> getFiles() {
        return files;
    }

    public void setFiles(Set<File> photoes) {
        this.files = photoes;
    }

    public Set<ApplicationDataViolation> getViolations() {
        return violations;
    }

    public void setViolations(Set<ApplicationDataViolation> violations) {
        this.violations = violations;
    }

    public Set<Application> getApplications() {
        return applications;
    }

    public void setApplications(Set<Application> applications) {
        this.applications = applications;
    }

    @Override
    public String toString() {
        return "ApplicationData [id_application_data=" + applicationDataId + ", information=" + information
                + "photoes=" + files + "]";
    }
}
