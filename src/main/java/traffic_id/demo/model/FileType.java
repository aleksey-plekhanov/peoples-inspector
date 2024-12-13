package traffic_id.demo.model;

import java.util.LinkedHashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;

@Entity
@Table(name = "file_type")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "typeName")
public class FileType {
    
    @Id
    @Column(name="Тип файла", nullable = false, columnDefinition="varchar(2083)", unique=true)
    private String typeName;

    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "type")
    private Set<File> files = new LinkedHashSet<>();

    public FileType(String typeName) {
        this.typeName = typeName;
    }

    public FileType() {
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Set<File> getFiles() {
        return files;
    }

    public void setFiles(Set<File> files) {
        this.files = files;
    }

    @Override
    public String toString() {
        return "FileType [typeName=" + typeName + "]";
    }
}
