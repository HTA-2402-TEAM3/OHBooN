<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="../include/header.jsp" %>
<div class="container">
    <h2 class="mt-5 mb-5"> INDEX PAGE </h2>
  <div>
    <button type="submit" class="btn btn-light" onclick="local.href='/board/list'">패스트 매칭 하러 가기 >>></button>
  </div>

    <p>Session Email :  <%=session.getAttribute("sessionEmail")%> </p>
    <p>Session Nickname :  <%=session.getAttribute("sessionNickname")%> </p>
    <p>Session Profile :  <%=session.getAttribute("sessionProfile")%> </p>
    <p>Session Grade :  <%=session.getAttribute("sessionGrade")%> </p>

</div>
<%@ include file="../include/footer.jsp"%>
