package by.epamtc.payment.view.tag;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class InfoMessageTag extends TagSupport {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int doStartTag() {
        try {
            JspWriter out = pageContext.getOut();
            out.write("<div id=\"openModal\" class=\"modalDialog\">");
            out.write("<div>");
            out.write("<a href=\"#openModal\" title=\"Закрыть\" class=\"close\">X</a>");
            out.write("<h2>System Info</h2>");
            out.write(message);
            out.write("</div>");
            out.write("</div>");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SKIP_BODY;
    }
}
