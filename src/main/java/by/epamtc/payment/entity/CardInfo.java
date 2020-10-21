package by.epamtc.payment.entity;


import java.math.BigDecimal;

public class CardInfo extends Card {

    private BigDecimal balance;
    private Currency currency;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CardInfo cardInfo = (CardInfo) o;
        return getBalance().equals(cardInfo.getBalance()) &&
                getCurrency() == cardInfo.getCurrency();

    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result;

        result = super.hashCode();
        result = result * PRIME + getBalance().hashCode();
        result = result * PRIME + getCurrency().hashCode();

        return result;
    }
}
