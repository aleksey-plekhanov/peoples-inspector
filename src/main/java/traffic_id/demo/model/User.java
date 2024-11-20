package traffic_id.demo.model;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import jakarta.persistence.*;

@Entity
//@Table(name = "user", schema = "traffic_offensive")
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_user;

    @Column(name="Фамилия", nullable = false, columnDefinition="VARCHAR(50)")
    private String surname;

    @Column(name="Имя", nullable = false, columnDefinition="VARCHAR(50)")
    private String name;

    @Column(name="Отчество", columnDefinition="VARCHAR(50)")
    private String patronymic;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Данные")
    private UserData data;

    @Column(nullable = false, columnDefinition="date")
    private Date registration;

    @OneToMany(mappedBy = "user")
    private Set<Moderator> moderators = new LinkedHashSet<>();

    public User(Integer id_user, String surname, String name, String patronymic, UserData data, Date registration) {
        this.id_user = id_user;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.data = data;
        this.registration = registration;
    }

    public User() {
    }

    public Integer getId() {
        return id_user;
    }

    public void setId(Integer id_user) {
        this.id_user = id_user;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public UserData getData() {
        return data;
    }

    public void setData(UserData data) {
        this.data = data;
    }

    public Date getRegistration() {
        return registration;
    }

    public void setRegistration(Date registration) {
        this.registration = registration;
    }
    
    public Set<Moderator> getModerators() {
        return moderators;
    }

    public void setModerators(Set<Moderator> moderators) {
        this.moderators = moderators;
    }

    @Override
    public String toString() {
        return "User [id_user=" + id_user + ", surname=" + surname + ", name=" + name + ", patronymic=" + patronymic
                + ", data=" + data + ", registration=" + registration + "]";
    }
}
