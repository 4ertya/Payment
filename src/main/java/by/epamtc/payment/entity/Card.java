package by.epamtc.payment.entity;

import java.util.Date;

public class Card {
    private long id;
    private long number;
    private Date expDate;
    private String ownerName;
    private String ownerSurname;
    private int pin;
    private int cvv;
    private String accountNumber;
    private Status status;
    private PaymentSystem paymentSystem;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerSurname() {
        return ownerSurname;
    }

    public void setOwnerSurname(String ownerSurname) {
        this.ownerSurname = ownerSurname;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public PaymentSystem getPaymentSystem() {
        return paymentSystem;
    }

    public void setPaymentSystem(PaymentSystem paymentSystem) {
        this.paymentSystem = paymentSystem;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Card)) {
            return false;
        }
        Card card = (Card) obj;
        return getPin() == card.getPin() &&
                getCvv() == card.getCvv() &&
                getNumber() == card.getNumber() &&
                getExpDate().equals(card.getExpDate()) &&
                getOwnerName().equals(card.getOwnerName()) &&
                getOwnerSurname().equals(card.getOwnerSurname()) &&
                getAccountNumber().equals(card.getAccountNumber()) &&
                getStatus() == card.getStatus() &&
                getPaymentSystem() == card.getPaymentSystem();
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;

        result = result * PRIME + Long.valueOf(getNumber()).hashCode();
        result = result * PRIME + getExpDate().hashCode();
        result = result * PRIME + getOwnerName().hashCode();
        result = result * PRIME + getOwnerSurname().hashCode();
        result = result * PRIME + getPin();
        result = result * PRIME + getCvv();
        result = result * PRIME + getAccountNumber().hashCode();
        result = result * PRIME + getStatus().hashCode();
        result = result * PRIME + getPaymentSystem().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Card:" +
                "number= " + number +
                ", expDate= " + expDate +
                ", ownerName= " + ownerName +
                ", ownerSurname= " + ownerSurname +
                ", account= " + accountNumber +
                ", status= " + status +
                ", paymentSystem= " + paymentSystem;
    }
}
