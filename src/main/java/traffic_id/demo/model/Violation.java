package traffic_id.demo.model;

import jakarta.persistence.*;

@Entity
//@Table(name = "violation", schema = "traffic_offensive")
@Table(name = "violation")
public class Violation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_violation;

    @Column(name="Статья", nullable = false, columnDefinition="varchar(20)", unique=true)
    private String article;

    @Column(name="Название", nullable = false, columnDefinition="text", unique=true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Вид", nullable = false)
    private TypeViolation type;

    @Column(name="Наказание", nullable = false, columnDefinition="text")
    private String punishment;

    public Violation(Integer id_violation, String article, String name, TypeViolation type, String punishment) {
        this.id_violation = id_violation;
        this.article = article;
        this.name = name;
        this.type = type;
        this.punishment = punishment;
    }

    public Violation() {
    }

    public Integer getId() {
        return id_violation;
    }

    public void setId(Integer id_violation) {
        this.id_violation = id_violation;
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

    public TypeViolation getType() {
        return type;
    }

    public void setType(TypeViolation type) {
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
        return "Violation [id_violation=" + id_violation + ", article=" + article + ", name=" + name + ", type=" + type
                + ", punishment=" + punishment + "]";
    }
}
