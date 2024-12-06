package traffic_id.demo.model;

import java.util.LinkedHashSet;
import java.util.Set;

import jakarta.persistence.*;

@Entity
@Table(name = "status")
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_status;

    @Column(name="Статус", nullable = false, columnDefinition="VARCHAR(50)", unique=true)
    private String status;

    @OneToMany(mappedBy = "status")
    private Set<Application> applications = new LinkedHashSet<>();

    public Status(Integer id_status, String status) {
        this.id_status = id_status;
        this.status = status;
    }

    public Status() {
    }

    public Integer getId() {
        return id_status;
    }

    public void setId(Integer id_status) {
        this.id_status = id_status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getId_status() {
        return id_status;
    }

    public void setId_status(Integer id_status) {
        this.id_status = id_status;
    }

    public Set<Application> getApplications() {
        return applications;
    }

    public void setApplications(Set<Application> applications) {
        this.applications = applications;
    }

    @Override
    public String toString() {
        return "Status [id_status=" + id_status + ", status=" + status + ", applications=" + applications + "]";
    }

}