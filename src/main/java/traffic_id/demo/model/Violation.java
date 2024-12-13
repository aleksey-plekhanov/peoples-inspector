package traffic_id.demo.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;

@Entity
@Table(name = "violation")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "article")
public class Violation {

    @Id
    @Column(name="Статья", nullable = false, columnDefinition="varchar(20)", unique=true)
    private String article;

    @Column(name="Название", nullable = false, columnDefinition="text", unique=true)
    private String name;

    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(cascade=CascadeType.ALL, optional = false)
    @JoinColumn(name = "Вид", nullable = true, unique=false)
    private ViolationType type;

    @Column(name="Наказание", nullable = false, columnDefinition="text")
    private String punishment;

    public Violation(String article, String name, ViolationType type, String punishment) {
        this.article = article;
        this.name = name;
        this.type = type;
        this.punishment = punishment;
    }

    public Violation() {
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ViolationType getType() {
        return type;
    }

    public void setType(ViolationType type) {
        this.type = type;
    }

    public String getPunishment() {
        return punishment;
    }

    public void setPunishment(String punishment) {
        this.punishment = punishment;
    }

    @Override
    public String toString() {
        return "Violation [article=" + article + ", name=" + name + ", type=" + type.getTypeName() + ", punishment=" + punishment + "]";
    }
}
