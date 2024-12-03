package traffic_id.demo.model;

import java.util.LinkedHashSet;
import java.util.Set;

import jakarta.persistence.*;

@Entity
@Table(name = "violation_type")
public class ViolationType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_type;

    @Column(name="Вид", nullable = false, columnDefinition="VARCHAR(128)", unique=true)
    private String type;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "type")
    private Set<Violation> violations = new LinkedHashSet<>();

    public ViolationType() {
    }

    public ViolationType(Integer id_type, String type) {
        this.id_type = id_type;
        this.type = type;
    }

    public Integer getId() {
        return id_type;
    }

    public void setId(Integer id_type) {
        this.id_type = id_type;
    }

    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public Set<Violation> getViolations() {
        return violations;
    }
    
    public void setViolations(Set<Violation> violations) {
        this.violations = violations;
    }

    @Override
    public String toString() {
        return "ViolationType [id_type=" + id_type + ", type=" + type + ", violations=" + violations + "]";
    }
}
