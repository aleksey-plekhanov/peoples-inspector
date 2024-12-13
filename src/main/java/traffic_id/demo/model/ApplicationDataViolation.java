package traffic_id.demo.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;

@Entity
@Table(name = "application_data_violation")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class ApplicationDataViolation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ApplicationDataViolationId;

    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_application_data", nullable = false, unique=false)
    private ApplicationData applicationData;

    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_violation", nullable = false, unique=false)
    private Violation violation;

    public ApplicationDataViolation(Integer ApplicationDataViolationId, ApplicationData applicationData, Violation violation) {
        this.ApplicationDataViolationId = ApplicationDataViolationId;
        this.applicationData = applicationData;
        this.violation = violation;
    }

    public ApplicationDataViolation() {
    }

    public ApplicationData getApplicationData() {
        return applicationData;
    }

    public void setApplicationData(ApplicationData applicationData) {
        this.applicationData = applicationData;
    }

    public Violation getViolation() {
        return violation;
    }

    public void setViolation(Violation violation) {
        this.violation = violation;
    }

    public Integer getId() {
        return ApplicationDataViolationId;
    }

    public void setId(Integer ApplicationDataViolationId) {
        this.ApplicationDataViolationId = ApplicationDataViolationId;
    }
    
    @Override
    public String toString() {
        return "ApplicationDataViolation [ApplicationDataViolationId="+ ApplicationDataViolationId + "applicationData=" 
        + applicationData + ", violation=" + violation + "]";
    }
}
