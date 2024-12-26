package traffic_id.demo.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;

@Entity
@Table(name = "application_violation")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class ApplicationViolation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_application_violation")
    private Integer ApplicationViolationId;

    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_application", nullable = false, unique=false)
    private Application application;

    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Статья", nullable = false, unique=false)
    private Violation violation;

    public ApplicationViolation(Integer ApplicationDataViolationId, Application applicationData, Violation violation) {
        this.ApplicationViolationId = ApplicationDataViolationId;
        this.application = applicationData;
        this.violation = violation;
    }

    public ApplicationViolation() {
    }

    public Application getApplication() {
        return application;
    }

    public void setApplicationData(Application applicationId) {
        this.application = applicationId;
    }

    public Violation getViolation() {
        return violation;
    }

    public void setViolation(Violation violation) {
        this.violation = violation;
    }

    public Integer getId() {
        return ApplicationViolationId;
    }

    public void setId(Integer ApplicationDataViolationId) {
        this.ApplicationViolationId = ApplicationDataViolationId;
    }
    
    @Override
    public String toString() {
        return "ApplicationDataViolation [ApplicationDataViolationId="+ ApplicationViolationId + "application=" 
        + application + ", violation=" + violation + "]";
    }
}
