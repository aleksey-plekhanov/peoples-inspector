package traffic_id.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "application_data_audio")
public class ApplicationDataAudio {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_application_data_audio;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_application_data", nullable = false, unique=false)
    private ApplicationData applicationData;

    @Column(name="Аудио", nullable = false, columnDefinition="varchar(2083)")
    private String audio;

    public ApplicationDataAudio(Integer id_application_data_audio, ApplicationData applicationData, String audio) {
        this.id_application_data_audio = id_application_data_audio;
        this.applicationData = applicationData;
        this.audio = audio;
    }

    public ApplicationDataAudio() {
    }

    public ApplicationData getApplicationData() {
        return applicationData;
    }

    public void setApplicationData(ApplicationData applicationData) {
        this.applicationData = applicationData;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public Integer getId_application_data_audio() {
        return id_application_data_audio;
    }

    public void setId_application_data_audio(Integer id_application_data_audio) {
        this.id_application_data_audio = id_application_data_audio;
    }

    @Override
    public String toString() {
        return "ApplicationDataAudio [id_application_data_audio=" + id_application_data_audio 
        + "applicationData=" + applicationData + ", audio=" + audio + "]";
    }
}
