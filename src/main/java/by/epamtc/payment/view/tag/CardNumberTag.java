package by.epamtc.payment.view.tag;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class CardNumberTag extends TagSupport {

    private Long cardNumber;

    public Long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public int doStartTag() {
        try {
            JspWriter out = pageContext.getOut();
            out.write(reformatCardNumber(cardNumber));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SKIP_BODY;
    }

    private String reformatCardNumber(Long cardNumber) {
        long lastDigits = cardNumber % 10000;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("xxxx")
                .append(" ")
                .append(lastDigits);
        return stringBuilder.toString();
    }
}
