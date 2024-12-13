package traffic_id.demo.model;

import java.sql.Types;
import java.util.Arrays;

import org.hibernate.annotations.JdbcTypeCode;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "user_data")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class UserData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userDataId;

    @Column(name="Логин", nullable = false, columnDefinition="VARCHAR(128)", unique=true)
    private String login;

    @JsonIgnore
    @Size(min=8, message = "Не меньше 8 знаков")
    @Column(name="Пароль", nullable = false, columnDefinition="VARCHAR(128)")
    private String password;

    @Column(name="Электронная почта", nullable = false, columnDefinition="VARCHAR(128)", unique=true)
    private String email;

    @JdbcTypeCode(Types.BINARY)
    @Column(name="Аватар", columnDefinition="bytea")
    private byte[] avatar;

    @JsonIgnore
    @Transient
    private String passwordConfirm;

    @Column(name="Роль", columnDefinition="VARCHAR(50)")
    private String roles;

    @JsonIdentityReference(alwaysAsId = true)
    @OneToOne(mappedBy = "data", cascade = CascadeType.ALL)
    private User user;

    public UserData(Integer userDataId, String login, String password, String email, byte[] avatar, String roles) {
        this.userDataId = userDataId;
        this.login = login;
        this.password = password;
        this.email = email;
        this.avatar = avatar;
        this.roles = roles;
    }

    public UserData() {
    }

    public Integer getId() {
        return userDataId;
    }

    public void setId(Integer userDataId) {
        this.userDataId = userDataId;
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

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserData [userDataId=" + userDataId + ", login=" + login + ", email=" + email 
                + ", avatar=" + Arrays.toString(avatar) + ", roles=" + roles + ", user=" + user.getId() + "]";
    }

}
