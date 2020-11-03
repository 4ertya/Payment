package by.epamtc.payment.entity;

import java.io.Serializable;

/**
 * This class describes users with properties
 * <b>id</b>
 * <b>login</b>,
 * <b>password</b>,
 * <b>email</b>,
 * <b>role</b>
 * <b>status</b>
 *
 * @author Dmitry Palchynski
 * @version 1.0 16 Sep 2020
 */

public class User implements Serializable {

    private long id;
    private String name;
    private String surname;
    private Role role;
    private Status status;


    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return id == user.id &&
                name.equals(user.name) &&
                surname.equals(user.surname) &&
                role == user.role;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;

        result = result * PRIME + Long.hashCode(id);
        result = result * PRIME + name.hashCode();
        result = result * PRIME + surname.hashCode();
        result = result * PRIME + role.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User: " +
                "id= " + id +
                ", name= " + name +
                ", surname= " + surname +
                ", role= " + role;
    }
}
