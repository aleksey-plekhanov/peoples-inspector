package traffic_id.demo.model;

import java.util.LinkedHashSet;
import java.util.Set;

import jakarta.persistence.*;

@Entity
@Table(name = "application_data")
public class ApplicationData {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_application_data;
     
    @Column(name="Информация", nullable = false, columnDefinition="text")
    private String information;

    @OneToMany(mappedBy = "applicationData")
    private Set<ApplicationDataAudio> audios = new LinkedHashSet<>();

    @OneToMany(mappedBy = "applicationData")
    private Set<ApplicationDataPhoto> photoes = new LinkedHashSet<>();

    @OneToMany(mappedBy = "applicationData")
    private Set<ApplicationDataVideo> videos = new LinkedHashSet<>();

    @OneToMany(mappedBy = "applicationData")
    private Set<ApplicationDataViolation> violations = new LinkedHashSet<>();

    @OneToMany(mappedBy = "application_data")
    private Set<Application> applications = new LinkedHashSet<>();

    public ApplicationData(Integer id_application_data, String information) {
        this.id_application_data = id_application_data;
        this.information = information;
    }

    public ApplicationData() {
    }

    public Integer getId_application_data() {
        return id_application_data;
    }

    public void setId_application_data(Integer id_application_data) {
        this.id_application_data = id_application_data;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public Set<ApplicationDataAudio> getAudios() {
        return audios;
    }

    public void setAudios(Set<ApplicationDataAudio> audios) {
        this.audios = audios;
    }

    public Set<ApplicationDataPhoto> getPhotoes() {
        return photoes;
    }

    public void setPhotoes(Set<ApplicationDataPhoto> photoes) {
        this.photoes = photoes;
    }

    public Set<ApplicationDataVideo> getVideos() {
        return videos;
    }

    public void setVideos(Set<ApplicationDataVideo> videos) {
        this.videos = videos;
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
        return "ApplicationData [id_application_data=" + id_application_data + ", information=" + information
                + ", audios=" + audios + ", photoes=" + photoes + ", videos=" + videos + ", violations=" + violations
                + ", applications=" + applications + "]";
    }
}
