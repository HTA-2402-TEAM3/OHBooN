<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="../include/header.jsp" %>
<div class="container">
    <h2 class="mt-5 mb-5"> INDEX PAGE </h2>

    <p>Session Email :  <%=session.getAttribute("sessionEmail")%> </p>
    <p>Session Nickname :  <%=session.getAttribute("sessionNickname")%> </p>
    <p>Session Profile :  <%=session.getAttribute("profile")%> </p>
    <p>Session Grade :  <%=session.getAttribute("grade")%> </p>

</div>
<%@ include file="../include/footer.jsp"%>