package rs.raf.dmilutinovic10518rn.wpseptembar.entities;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserFormEdit {
    @NotNull(message = "You must provide the user ID")
    private int id;

    @NotNull(message = "You must provide the first name")
    @NotEmpty(message = "You must provide the first name")
    private String firstName;

    @NotNull(message = "You must provide the last name")
    @NotEmpty(message = "You must provide the last name")
    private String lastName;

    @NotNull(message = "You must provide an email")
    @NotEmpty(message = "You must provide an email")
    @Email(message = "You must provide a valid email")
    private String email;

    private String password;
    private String confirmPassword;

    @NotNull(message = "Is Admin field is required")
    private boolean isAdmin;

    @NotNull(message = "Is Active field is required")
    private boolean isActive;

    public UserFormEdit() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
