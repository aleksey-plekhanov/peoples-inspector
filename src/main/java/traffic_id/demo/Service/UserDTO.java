package traffic_id.demo.service;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import traffic_id.demo.model.User;
import traffic_id.demo.model.UserData;
import traffic_id.demo.validation.annotation.ValidEmail;

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

    private String avatar;
    
    public UserDto(User user) {
        surname = user.getSurname();
        name = user.getName();
        patronymic = user.getPatronymic();
        UserData userData = user.getData();
        email = userData.getEmail();
        login = userData.getLogin();
        avatar = userData.getAvatar();
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}

