package traffic_id.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "application_data_photo")
public class ApplicationDataPhoto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_application_data_photo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_application_data", nullable = false, unique=false)
    private ApplicationData applicationData;

    @Column(name="Фото", nullable = false, columnDefinition="varchar(2083)")
    private String photo;

    public ApplicationDataPhoto(Integer id_application_data_photo, ApplicationData applicationData, String photo) {
        this.id_application_data_photo = id_application_data_photo;
        this.applicationData = applicationData;
        this.photo = photo;
    }

    public ApplicationDataPhoto() {
    }

    public ApplicationData getApplicationData() {
        return applicationData;
    }

    public void setApplicationData(ApplicationData applicationData) {
        this.applicationData = applicationData;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getId_application_data_photo() {
        return id_application_data_photo;
    }

    public void setId_application_data_photo(Integer id_application_data_photo) {
        this.id_application_data_photo = id_application_data_photo;
    }

    @Override
    public String toString() {
        return "ApplicationDataPhoto [id_application_data_photo=" + id_application_data_photo 
        + "applicationData=" + applicationData + ", photo=" + photo + "]";
    }
}
