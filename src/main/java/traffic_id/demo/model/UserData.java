package traffic_id.demo.model;

import jakarta.persistence.*;

@Entity
//@Table(name = "user_data", schema = "traffic_offensive")
@Table(name = "user_data")
public class UserData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_users_data;

    @Column(name="Логин", nullable = false, columnDefinition="VARCHAR(128)", unique=true)
    private String login;

    @Column(name="Пароль", nullable = false, columnDefinition="VARCHAR(128)")
    private String password;

    @Column(name="Электронная почта", nullable = false, columnDefinition="VARCHAR(128)", unique=true)
    private String email;

    @Lob
    @Column(name="Аватар", nullable = false, columnDefinition="bytea")
    private String avatar;

    public UserData(Integer id_users_data, String login, String password, String email) {
        this.id_users_data = id_users_data;
        this.login = login;
        this.password = password;
        this.email = email;
    }

    public UserData() {
    }

    public Integer getId() {
        return id_users_data;
    }

    public void setId(Integer id_users_data) {
        this.id_users_data = id_users_data;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "UsersData [id_users_data=" + id_users_data + ", login=" + login + ", password=" + password + ", email="
                + email + ", avatar=" + avatar + "]";
    }
}
