package traffic_id.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "application_data_violation")
public class ApplicationDataViolation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_application_data_violation;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_application_data", nullable = false, unique=false)
    private ApplicationData applicationData;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_violation", nullable = false, unique=false)
    private Violation violation;

    public ApplicationDataViolation(Integer id_application_data_violation, ApplicationData applicationData, Violation violation) {
        this.id_application_data_violation = id_application_data_violation;
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

    public Integer getId_application_data_violation() {
        return id_application_data_violation;
    }

    public void setId_application_data_violation(Integer id_application_data_violation) {
        this.id_application_data_violation = id_application_data_violation;
    }
    
    @Override
    public String toString() {
        return "ApplicationDataViolation [id_application_data_violation="+ id_application_data_violation + "applicationData=" 
        + applicationData + ", violation=" + violation + "]";
    }
}
