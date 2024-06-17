<%--
  Created by IntelliJ IDEA.
  User: JHTA
  Date: 2024-06-13
  Time: 오전 11:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
<body>
<h1>채팅방 목록</h1>
<table>

    <tr>
        <th>프로필</th>
        <th>리스트</th>
    </tr>

    <c:forEach items="${requestScope.chatList}" var="eachChat">
        <tr>
            <td>
                <div>${eachChat.receiver}님의 프로필</div>
            </td>
            <td>
                <a href="chatroomtest.jsp?chatID=${eachChat.chatID}">
                    <div>${eachChat.receiver}님 과의 대화</div>
                    <div>${eachChat.receiver}님 과의 마지막 대화 내용</div>
                </a>
            </td>
        </tr>
    </c:forEach>

</table>
<form action="chatroomtest.jsp" method="get">
    <input type="text">
    <button>채팅 입장</button>
</form>
</body>
</head>
</html>


