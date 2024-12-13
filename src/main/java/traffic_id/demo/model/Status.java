package traffic_id.demo.model;

import java.util.LinkedHashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;

@Entity
@Table(name = "status")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "statusName")
public class Status {

    @Id
    @Column(name="Статус", nullable = false, columnDefinition="VARCHAR(25)", unique=true)
    private String statusName;

    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "status")
    private Set<Application> applications = new LinkedHashSet<>();

    public Status(String statusName) {
        this.statusName = statusName;
    }

    public Status() {
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Set<Application> getApplications() {
        return applications;
    }

    public void setApplications(Set<Application> applications) {
        this.applications = applications;
    }

    @Override
    public String toString() {
        return "Status [statusName=" + statusName + "]";
    }

}
