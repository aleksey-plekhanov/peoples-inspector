package traffic_id.demo.service;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import traffic_id.demo.model.User;
import traffic_id.demo.model.UserData;
import traffic_id.demo.validation.annotation.PasswordMatches;

@PasswordMatches()
public class PasswordDto {

    @NotNull
    @NotEmpty
    @Size(min=6, message = "Не меньше 6 знаков")
    private String password;
    private String matchingPassword;

    public PasswordDto(User user) {
        UserData userData = user.getData();
        password = userData.getPassword();
    }

    public PasswordDto() {
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
