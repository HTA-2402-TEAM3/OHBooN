<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="../include/header.jsp" %>
<div class="container">
    <h2 class="mt-5 mb-5"> INDEX PAGE </h2>
    <%=session.getAttribute("sessionNickname")%>
</br>
    <%=session.getAttribute("sessionEmail")%>
</div>
<%@ include file="../include/footer.jsp"%>