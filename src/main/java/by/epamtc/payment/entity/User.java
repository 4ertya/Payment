package by.epamtc.payment.entity;

/**
 * This class describes users with properties
 * <b>id</b>
 * <b>login</b>,
 * <b>password</b>,
 * <b>email</b>,
 * <b>role</b>
 *
 * @author Dmitry Palchynski
 * @version 1.0 16 Sep 2020
 */

public class User {

    private long id;
    private String login;
    private String password;
    private String email;
    private Role role;


    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return id == user.id &&
                login.equals(user.login) &&
                password.equals(user.password) &&
                email.equals(user.email) &&
                role == user.role;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;

        result = result * PRIME + Long.hashCode(id);
        result = result * PRIME + login.hashCode();
        result = result * PRIME + password.hashCode();
        result = result * PRIME + email.hashCode();
        result = result * PRIME + role.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User: " +
                "id= " + id +
                ", login= " + login +
                ", email= " + email +
                ", role= " + role;
    }
}
