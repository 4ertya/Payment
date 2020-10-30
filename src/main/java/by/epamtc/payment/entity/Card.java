package by.epamtc.payment.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Card {
    private Long id;
    private Long number;
    private Date expDate;
    private String ownerName;
    private String ownerSurname;
    private Integer pin;
    private Integer cvv;
    private String accountNumber;
    private Status status;
    private PaymentSystem paymentSystem;
    private BigDecimal balance;
    private Currency currency;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
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

    public Integer getPin() {
        return pin;
    }

    public void setPin(Integer pin) {
        this.pin = pin;
    }

    public Integer getCvv() {
        return cvv;
    }

    public void setCvv(Integer cvv) {
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

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
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
        return getNumber().equals(card.getNumber()) &&
                getOwnerName().equals(card.getOwnerName()) &&
                getOwnerSurname().equals(card.getOwnerSurname()) &&
                getAccountNumber().equals(card.getAccountNumber());
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;

        result = result * PRIME + getNumber().hashCode();
        result = result * PRIME + getOwnerName().hashCode();
        result = result * PRIME + getOwnerSurname().hashCode();
        result = result * PRIME + getAccountNumber().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Card:" +
                "number= " + getNumber() +
                ", expDate= " + getExpDate() +
                ", ownerName= " + getOwnerName() +
                ", ownerSurname= " + getOwnerSurname() +
                ", account= " + getAccountNumber() +
                ", status= " + getStatus() +
                ", paymentSystem= " + getPaymentSystem() +
                ", currency= " + getCurrency() +
                ", balance= " + getBalance();
    }
}
