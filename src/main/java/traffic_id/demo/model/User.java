package traffic_id.demo.model;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Integer id;

    @Column(name="Фамилия", nullable = false, columnDefinition="VARCHAR(50)")
    private String surname;

    @Column(name="Имя", nullable = false, columnDefinition="VARCHAR(50)")
    private String name;

    @Column(name="Отчество", columnDefinition="VARCHAR(50)")
    private String patronymic;

    @OneToOne(cascade=CascadeType.ALL, optional = false)
    @JoinColumn(name = "Данные")
    private UserData data;

    @Column(name = "Дата регистрации", nullable = true, columnDefinition="date")
    private Date registration;

    public User(Integer userId, String surname, String name, String patronymic, UserData data, Date registration) {
        this.id = userId;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.data = data;
        this.registration = registration;
    }

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer userId) {
        this.id = userId;
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

    @Override
    public String toString() {
        return "User [userId=" + id + ", surname=" + surname + ", name=" + name + ", patronymic=" + patronymic
                + ", data=" + data.getId() + ", registration=" + registration + "]";
    }
}
