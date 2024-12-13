package traffic_id.demo.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;

@Entity
@Table(name = "file")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class File {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fileId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_application_data", nullable = false, unique=false)
    private ApplicationData applicationData;

    @Column(name="Путь", nullable = false, columnDefinition="varchar")
    private String path;

    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Тип файла", nullable = false, unique=false)
    private FileType type;

    public File(Integer fileId, ApplicationData applicationData, String photo, String path, FileType type) {
        this.fileId = fileId;
        this.applicationData = applicationData;
        this.path = path;
        this.type = type;
    }

    public File() {
    }

    public Integer getId() {
        return fileId;
    }

    public void setId(Integer fileId) {
        this.fileId = fileId;
    }

    public ApplicationData getApplicationData() {
        return applicationData;
    }

    public void setApplicationData(ApplicationData applicationData) {
        this.applicationData = applicationData;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public FileType getType() {
        return type;
    }

    public void setType(FileType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ApplicationDataPhoto [fileId=" + fileId 
        + "applicationData=" + applicationData + ", path=" + path + ", type=" + type + "]";
    }
}
