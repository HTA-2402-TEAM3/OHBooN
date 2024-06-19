<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="../include/header.jsp" %>
<div class="container">
    <h2 class="mt-5 mb-5"> ☆ Admin Home ☆ </h2>

    <li><a href="../admin/userList" class="btn btn-outline-info mt-2">회원 목록</a></li>
    <li><a href="../admin/home" class="btn btn-outline-info mt-2">넣고 싶은 항목1</a></li>
    <li><a href="../admin/home" class="btn btn-outline-info mt-2">넣고 싶은 항목2</a></li>
    <li><a href="../admin/home" class="btn btn-outline-info mt-2">넣고 싶은 항목3</a></li>
    <li><a href="../admin/statistics" class="btn btn-outline-info mt-2">통계</a></li>



    <p>Session Email :  <%=session.getAttribute("sessionEmail")%> </p>
    <p>Session Nickname :  <%=session.getAttribute("sessionNickname")%> </p>
    <p>Session Profile :  <%=session.getAttribute("profile")%> </p>
    <p>Session Grade :  <%=session.getAttribute("sessionGrade")%> </p>

</div>
<%@ include file="../include/footer.jsp"%>
