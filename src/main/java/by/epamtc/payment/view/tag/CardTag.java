package by.epamtc.payment.view.tag;

import by.epamtc.payment.entity.Card;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.text.SimpleDateFormat;


public class CardTag extends TagSupport {

    private final static String VISA_IMG = "img/visaClassic.jpg";
    private final static String MASTERCARD_IMG = "img/masterWorld.jpg";
    private final static String VISA_NAME = "VISA";
    private final static String MASTERCARD_NAME = "MASTERCARD";
    private Card card;

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    @Override
    public int doStartTag() {
        try {
            JspWriter out = pageContext.getOut();
            if (card.getPaymentSystem().name().equals(VISA_NAME)) {
                out.write("<div class=\"visa\">");
                out.write("<a class=\"select_card\" " +
                        "href=\"UserController?command=to_card_info_page&card_id=" + card.getId() + "\"></a>");
                out.write("<img src=\"" + VISA_IMG + "\" width=\"250px\" height=\"150px\">");
                if (card.getStatus().name().equals("BLOCKED")) {
                    out.write("<p class=\"blocked\">Заблокирована</p>");
                }
                out.write("<p class=\"card_number\">" + card.getNumber() + "</p>");
                out.write("<p class=\"card_date\">" +
                        new SimpleDateFormat("MM/yy").format(card.getExpDate()) + "</p>");
                out.write("<p class=\"card_holder\">" + card.getOwnerName().toUpperCase() + " " +
                        card.getOwnerSurname().toUpperCase() + "</p>");
            }
            if (card.getPaymentSystem().name().equals(MASTERCARD_NAME)) {
                out.write("<div class=\"mastercard\">");
                out.write("<a class=\"select_card\" " +
                        "href=\"UserController?command=to_card_info_page&card_id=" + card.getId() + "\"></a>");
                out.write("<img src=\"" + MASTERCARD_IMG + "\" width=\"250px\" height=\"150px\">");
                if (card.getStatus().name().equals("BLOCKED")) {
                    out.write("<p class=\"blocked\">Заблокирована</p>");
                }
                out.write("<p class=\"card_number\">" + card.getNumber() + "</p>");
                out.write("<p class=\"card_date\">" +
                        new SimpleDateFormat("MM/yy").format(card.getExpDate()) + "</p>");
                out.write("<p class=\"card_holder\">" + card.getOwnerName().toUpperCase() + " " +
                        card.getOwnerSurname().toUpperCase() + "</p>");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SKIP_BODY;
    }
}
