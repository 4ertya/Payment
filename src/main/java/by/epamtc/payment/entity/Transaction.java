package by.epamtc.payment.entity;

import java.util.Date;

public class Transaction {
    private int id;
    private Date date;
    private double amount;
    private Currency currency;
    private String destination;
    private int cards_id;
//    private TransactionType transaction_types;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getCards_id() {
        return cards_id;
    }

    public void setCards_id(int cards_id) {
        this.cards_id = cards_id;
    }

//    public TransactionType getTransaction_types() {
//        return transaction_types;
//    }
//
//    public void setTransaction_types(TransactionType transaction_types) {
//        this.transaction_types = transaction_types;
//    }
}
