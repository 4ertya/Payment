package by.epamtc.payment.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Transaction {
    private Long id;
    private Date date;
    private BigDecimal amount;
    private Currency currency;
    private String destination;
    private Long cardNumber;
    private Long cardId;
    private TransactionType transactionType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
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

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cards_id) {
        this.cardId = cards_id;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public Long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
    //    public TransactionType getTransaction_types() {
//        return transaction_types;
//    }
//
//    public void setTransaction_types(TransactionType transaction_types) {
//        this.transaction_types = transaction_types;
//    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", date=" + date +
                ", amount=" + amount +
                ", currency=" + currency +
                ", destination='" + destination + '\'' +
                ", cardNumber=" + cardNumber +
                ", cardId=" + cardId +
                ", transactionType=" + transactionType +
                '}';
    }
}
