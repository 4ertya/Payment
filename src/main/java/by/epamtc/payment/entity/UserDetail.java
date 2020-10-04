package by.epamtc.payment.entity;

/**
 * This class describes users details with properties
 * <b>name</b>
 * <b>surname</b>,
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

    private String name;
    private String surname;
    private String gender;
    private String passportSeries;
    private String passportNumber;
    private String phoneNumber;
    private String location;

    public UserDetail() {
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassportSeries() {
        return passportSeries;
    }

    public void setPassportSeries(String passport_series) {
        this.passportSeries = passport_series;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passport_number) {
        this.passportNumber = passport_number;
    }

    public String getPhone_number() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phone_number) {
        this.phoneNumber = phone_number;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
        return name.equals(userDetail.name) &&
                surname.equals(userDetail.surname) &&
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

        result = result * PRIME + name.hashCode();
        result = result * PRIME + surname.hashCode();
        result = result * PRIME + gender.hashCode();
        result = result * PRIME + passportSeries.hashCode();
        result = result * PRIME + passportNumber.hashCode();
        result = result * PRIME + phoneNumber.hashCode();
        result = result * PRIME + location.hashCode();
        return result;
    }
}


