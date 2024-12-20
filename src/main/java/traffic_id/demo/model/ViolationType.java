package traffic_id.demo.model;

import java.util.LinkedHashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;

@Entity
@Table(name = "violation_type")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "typeName")
public class ViolationType {

    @Id
    @Column(name="Вид", nullable = false, columnDefinition="VARCHAR(128)", unique=true)
    private String typeName;

    public ViolationType() {
    }

    public ViolationType(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }
    
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "ViolationType [typeName=" + typeName + "]";
    }
}
