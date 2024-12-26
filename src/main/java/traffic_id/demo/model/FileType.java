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
    @Column(name="Тип файла", nullable = false, columnDefinition="varchar(5)", unique=true)
    private String typeName;

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

    @Override
    public String toString() {
        return "FileType [typeName=" + typeName + "]";
    }
}
