package by.epamtc.payment.entity;



import java.math.BigDecimal;
import java.util.Objects;

public class CardInfo extends Card {
    private String account_number;
    private BigDecimal balance;
    private String currency;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
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
        return Objects.equals(getAccount_number(), cardInfo.getAccount_number()) &&
                Objects.equals(getBalance(), cardInfo.getBalance());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getAccount_number(), getBalance());
    }
}
