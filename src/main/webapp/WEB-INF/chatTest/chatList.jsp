<%--
  Created by IntelliJ IDEA.
  User: Dayou
  Date: 2024-06-13
  Time: 오전 9:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>채팅</title>
    <h1>${sessionScope.sessionNickname}</h1>
</head>
<body>
<form action="makeChat" method="post">
    <input type="text" name="board_id" placeholder="board_id">
    <button>채팅방 생성 -> 게시글에서</button>
</form>
<form action="makeChat" method="post">
    <input type="text" name="receiverName">
    <button>채팅방 생성 -> 그냥 유저에서</button>
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
                <a>
                    <button onclick="enterChat(${ChatDTO.chatID}).then(() =>
                            matchBtn('${sessionScope.sessionNickname}'));">
                            ${subject}
                    </button>
                    <div>${subject}님 과의 마지막 대화 내용</div>
                </a>
            </td>
        </tr>
    </c:forEach>

</table>
<br/>

<button id="sendBtn" onclick="sendMessage();">Send Message</button>
<button id="closeBtn" onclick="closeSocket();">Close Socket</button>
<br/>
<input type="text" id="messageInput" placeholder="Enter message" name="sendMsg">
<br/>
<button id="matchBtn" onclick="matching(&{obj.match_id});">matching</button>
<br/>

<div id="messages"></div>
<script src="../../Chat.js"></script>
</body>
</html>