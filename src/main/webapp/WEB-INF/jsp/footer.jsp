<%--
  Created by IntelliJ IDEA.
  User: 4ertya
  Date: 02.10.2020
  Time: 14:09
  To change this template use File | Settings | File Templates.
--%>

<fmt:message bundle="${loc}" key="local.about" var="about"/>
<fmt:message bundle="${loc}" key="local.contacts" var="contacts"/>
<fmt:message bundle="${loc}" key="local.privacy" var="privacy"/>

<footer>
    <div class="navbar">
        <div class="footer-info">
            <div class="footer-block">
                <a href="MainController?command=to_about_page">${about}</a>
            </div>

            <div class="footer-block">
                <a href="#">${contacts}</a>
            </div>

            <div class="footer-block">
                <a href="#">${privacy}</a>
            </div>
        </div>
    </div>
</footer>

