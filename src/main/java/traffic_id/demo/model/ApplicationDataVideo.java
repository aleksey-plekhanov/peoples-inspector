package traffic_id.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "application_data_video")
public class ApplicationDataVideo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_application_data_video;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_application_data", nullable = false, unique=false)
    private ApplicationData applicationData;

    @Column(name="Видео", nullable = false, columnDefinition="varchar(2083)")
    private String video;

    public ApplicationDataVideo(Integer id_application_data_video, ApplicationData applicationData, String video) {
        this.id_application_data_video = id_application_data_video;
        this.applicationData = applicationData;
        this.video = video;
    }

    public ApplicationDataVideo() {
    }

    public ApplicationData getApplicationData() {
        return applicationData;
    }

    public void setApplicationData(ApplicationData applicationData) {
        this.applicationData = applicationData;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public Integer getId_application_data_video() {
        return id_application_data_video;
    }

    public void setId_application_data_video(Integer id_application_data_video) {
        this.id_application_data_video = id_application_data_video;
    }
    

    @Override
    public String toString() {
        return "ApplicationDataVideo [id_application_data_video="+ id_application_data_video 
        + "applicationData=" + applicationData + ", video=" + video + "]";
    }
}
