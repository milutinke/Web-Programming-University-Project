package rs.raf.dmilutinovic10518rn.wpseptembar.entities;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class LoginForm {
    @NotNull(message = "You must provide an email")
    @NotEmpty(message = "You must provide an email")
    @Email(message = "You must provide a valid email")
    private String email;

    @NotNull(message = "You must provide a password")
    @NotEmpty(message = "You must provide a password")
    private String password;

    public LoginForm() {
    }

    public LoginForm(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
