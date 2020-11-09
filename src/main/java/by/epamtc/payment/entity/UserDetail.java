package by.epamtc.payment.entity;

/**
 * This class describes users details with properties
 * <b>ruName</b>,
 * <b>ruSurname</b>,
 * <b>enName</b>,
 * <b>enSurname</b>,
 * <b>gender</b>,
 * <b>passport series</b>,
 * <b>passport number</b>,
 * <b>phone number</b>,
 * <b>location</b>.
 *
 * @author Dmitry Palchynski
 * @version 1.0 25 Sep 2020
 */

public class UserDetail {

    private Long id;
    private String ruName;
    private String ruSurname;
    private String enName;
    private String enSurname;
    private String gender;
    private String passportSeries;
    private Integer passportNumber;
    private String phoneNumber;
    private String location;
    private String email;
    private Role role;
    private Status status;

    public UserDetail() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRuName() {
        return ruName;
    }

    public void setRuName(String ruName) {
        this.ruName = ruName;
    }

    public String getRuSurname() {
        return ruSurname;
    }

    public void setRuSurname(String ruSurname) {
        this.ruSurname = ruSurname;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getEnSurname() {
        return enSurname;
    }

    public void setEnSurname(String enSurname) {
        this.enSurname = enSurname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassportSeries() {
        return passportSeries;
    }

    public void setPassportSeries(String passportSeries) {
        this.passportSeries = passportSeries;
    }

    public Integer getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(Integer passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        UserDetail userDetail = (UserDetail) obj;
        return id.equals(userDetail.id) &&
                ruName.equals(userDetail.ruName) &&
                ruSurname.equals(userDetail.ruSurname) &&
                enName.equals(userDetail.enName) &&
                enSurname.equals(userDetail.enSurname) &&
                gender.equals(userDetail.gender) &&
                passportSeries.equals(userDetail.passportSeries) &&
                passportNumber.equals(userDetail.passportNumber) &&
                phoneNumber.equals(userDetail.phoneNumber) &&
                location.equals(userDetail.location);
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;

        result = result * PRIME + id.hashCode();
        result = result * PRIME + ruName.hashCode();
        result = result * PRIME + ruSurname.hashCode();
        result = result * PRIME + enName.hashCode();
        result = result * PRIME + enSurname.hashCode();
        result = result * PRIME + gender.hashCode();
        result = result * PRIME + passportSeries.hashCode();
        result = result * PRIME + passportNumber.hashCode();
        result = result * PRIME + phoneNumber.hashCode();
        result = result * PRIME + location.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "UserDetail: " +
                "id= " + id +
                ", ruName= " + ruName +
                ", ruSurname= " + ruSurname +
                ", enName= " + enName +
                ", enSurname= " + enSurname +
                ", gender= " + gender +
                ", passportSeries= " + passportSeries +
                ", passportNumber= " + passportNumber +
                ", phoneNumber= " + phoneNumber +
                ", location= " + location +
                ", email= " + email +
                ", role= " + role +
                ", status= " + status;
    }
}


