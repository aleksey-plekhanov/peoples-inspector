package traffic_id.demo.service;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import traffic_id.demo.model.User;
import traffic_id.demo.model.UserData;
import traffic_id.demo.validation.annotation.PasswordMatches;
import traffic_id.demo.validation.annotation.ValidEmail;

@PasswordMatches()
public class UserDto {

    @NotNull
    @NotEmpty
    private String surname;

    @NotNull
    @NotEmpty
    private String name;
    private String patronymic;

    @ValidEmail
    @NotNull
    @NotEmpty
    private String email;

    @NotNull
    @NotEmpty
    @Size(min=2, message = "Не меньше 2 знаков")
    private String login;

    @NotNull
    @NotEmpty
    @Size(min=6, message = "Не меньше 6 знаков")
    private String password;
    private String matchingPassword;
    
    public UserDto(User user) {
        surname = user.getSurname();
        name = user.getName();
        patronymic = user.getPatronymic();
        UserData userData = user.getData();
        email = userData.getEmail();
        login = userData.getLogin();
        password = userData.getPassword();
    }

    public UserDto() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }
}
