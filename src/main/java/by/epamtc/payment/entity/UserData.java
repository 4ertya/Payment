package by.epamtc.payment.entity;

public class UserData {
    private long id;
    private String login;
    private String email;
    private Role role;


    public UserData() {
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
        UserData userData = (UserData) obj;
        return id == userData.id &&
                login.equals(userData.login) &&
                email.equals(userData.email) &&
                role == userData.role;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;

        result = result * PRIME + Long.hashCode(id);
        result = result * PRIME + login.hashCode();
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
