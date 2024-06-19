<%--
  Created by IntelliJ IDEA.
  User: JHTA
  Date: 2024-06-18
  Time: 오전 10:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>

<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>채팅</title>
    <link rel="stylesheet" href="/css/main-style.css">
    <script src="https://kit.fontawesome.com/a2e8ca0ae3.js" crossorigin="anonymous"></script>
</head>
<body>
<div class="full">
    <div class="match-button">
        <button id="MatchTarget">
            <i class="Match">매칭완료</i>
        </button>
    </div>
<%--채팅방 목록 왼쪽이 되야함--%>
    <div class="chat-area">
    <div class="chatting-list">
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
    </div>


<%--여기가 채팅창 오른쪽이 되야함--%>
<div id="container">
    <i div class="chatting-area">
    </i>
    <div class="input-area">
        <button onclick="sendMessage();" button id="send">Send Message</button>
        <input type="text" id="messageInput" row="3" placeholder="Enter message" name="sendMsg">
        <div id="messages"></div>
        <span>${msgList}</span>
    </div>
    <br/>
</div>
</div>
</div>













<script type="text/javascript">
    var websocket;
    var inputMessage = document.getElementById('messageInput');
    var sessionNickname = '<%=session.getAttribute("sessionNickname")%>';

    document.addEventListener("DOMContentLoaded", function () {
        if(sessionNickname && sessionNickname.trim() !== "") {
            // writeResponse("WebSocket is open!!!!");
            if (websocket !== undefined && websocket.readyState !== WebSocket.CLOSED) {
                writeResponse("WebSocket is already opened.");
                return;
            }
            websocket = new WebSocket("ws://localhost:8080/chat");
            websocket.onopen = function (event) {
                if (event.data === undefined) return;
                writeResponse(event.data);
            };
            websocket.onmessage = function (event) {
                var message = event.data.split(":");
                var sender = message[0];
                var content = message[1];
                console.log(message);
                var messages = document.getElementById("messages");
                messages.innerHTML += "<p class='chat_content'>" + sender + " : " + content + "</p>";
            };
            websocket.onclose = function (event) {
                // writeResponse("Connection closed");
            };
            websocket.onerror = function (event) {
                console.log(event);
            }
        } else {
            console.log("user is not login...");
        }
    });

    function sendMessage() {
        var text = document.getElementById("messageInput").value;
        var message = {
            match_id: "${match_id}",
            chat_id: "${chat_id}",
            user_id: "${user_id}",
            content: text,
            timestamp: new Date().toISOString()
        };
        websocket.send(JSON.stringify(message));
        inputMessage.value = "";
    }

    function closeSocket() {
        websocket.close();
    }

    function writeResponse(text) {
        var messages = document.getElementById("messages");
        messages.innerHTML += "<br/>" + text;
    }
</script>
</body>
</html>