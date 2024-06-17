<%--
  Created by IntelliJ IDEA.
  User: Dayou
  Date: 2024-06-13
  Time: 오전 9:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>

<html>
<head>
    <title>채팅</title>
    <h1>${sessionScope.sessionNickname}</h1>
</head>
<body>
<form action="makeChat" method="post">
    <input type="text" name="board_id" placeholder="board_id">
    <button>채팅방 생성</button>
</form>

<form action="enterChat" method="get">
    <input type="text" name=chat_id placeholder="chat_id">
<%--    <input type="text" name=user_id placeholder="user_id">--%>
    <button>채팅방 입장</button>
</form>
<h4>
</h4>
<table>
    <tr>
        <th>프로필</th>
        <th>리스트</th>
    </tr>
    <c:forEach items="${requestScope.chatList}" var="ChatDTO" varStatus="loop">
        <c:choose>
            <c:when test="${sessionScope.sessionNickname eq ChatDTO.sender}">
                <c:set var="subject" value="${ChatDTO.receiver}"/>
            </c:when>
            <c:otherwise>
                <c:set var="subject" value="${ChatDTO.sender}"/>
            </c:otherwise>
        </c:choose>
        <tr>
            <td>
                <div>${subject}님의 프로필</div>
            </td>
            <td>
                <a href="/enterChat?chat_id=${ChatDTO.chatID}">
                    <div>${subject}님 과의 대화</div>
                    <div>${subject}님 과의 마지막 대화 내용</div>
                </a>
            </td>
        </tr>
    </c:forEach>

</table>

<div id="messages"></div>

<script>
    var websocket;
    var inputMessage = document.getElementById('messageInput');
    var sessionNickname = '<%=session.getAttribute("sessionNickname")%>';

    document.addEventListener("DOMContentLoaded", function () {
        if(sessionNickname&&sessionNickname.trim()!=="") {
            // function openSocket() {
            writeResponse("WebSocket is open!!!!");
            // Ensure only one connection is open at a time
            if (websocket !== undefined && websocket.readyState !== WebSocket.CLOSED) {
                writeResponse("WebSocket is already opened.");
                return;
            }
            // Create a new instance of the websocket

            websocket = new WebSocket("ws://localhost:8080/chat");
            websocket.onopen = function (event) {
                if (event.data === undefined) return;
                writeResponse(event.data);
            };
        }else {
            console.log("user is not login...");
        }
    });
</script>
</body>
</html>