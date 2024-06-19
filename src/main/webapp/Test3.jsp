<%-- Created by IntelliJ IDEA. User: Dayou Date: 2024-06-13 Time: 오전 9:16 --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>채팅</title>
    <link rel="stylesheet" href="css/test3.css">
    <script src="https://kit.fontawesome.com/a2e8ca0ae3.js" crossorigin="anonymous"></script>
</head>
<body>
</form>
<div class="container">
    <div class="row">
        <section class="discussions">
            <div class="discussion search"
            <c:forEach items="${requestScope.chatList}" var="ChatDTO" varStatus="loop">
                <c:choose>
                    <c:when test="${sessionScope.sessionNickname eq ChatDTO.sender}">
                        <c:set var="subject" value="${ChatDTO.receiver}"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="subject" value="${ChatDTO.sender}"/>
                    </c:otherwise>
                </c:choose>
                <li>
                    <a href="/enterChat?chat_id=${ChatDTO.chatID}">
                        <img src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/1940306/chat_avatar_01.jpg" alt="">
                        <div>
                            <h2>${subject}님의 프로필</h2>
                            <h3>${subject}님 과의 대화</h3>
                                <%--                            <p>${subject}님 과의 마지막 대화 내용</p>--%>
                        </div>
                    </a>
                </li>
            </c:forEach>
        </section>
    </div>
</div>
<main>
    <ul id="messages">
        ${msgList}
        <%--            you는 receiver--%>
        <%--            <li class="you">--%>
        <%--                <div class="entete">--%>
        <%--                    <span class="status green"></span>--%>
        <%--                    <h2>Vincent</h2>--%>
        <%--                    <h3>10:12AM, Today</h3>--%>
        <%--                </div>--%>
        <%--                <div class="message">--%>
        <%--                    Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor.--%>
        <%--                </div>--%>
        <%--            </li>--%>
        <%--&lt;%&ndash;    me sender&ndash;%&gt;--%>
        <%--            <li class="me">--%>
        <%--                <div class="entete">--%>
        <%--                    <h3>10:12AM, Today</h3>--%>
        <%--                    <h2>Vincent</h2>--%>
        <%--                    <span class="status blue"></span>--%>
        <%--                </div>--%>
        <%--                <div class="triangle"></div>--%>
        <%--                <div class="message">--%>
        <%--                    Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor.--%>
        <%--                </div>--%>
        <%--            </li>--%>
        <%--            <li class="me">--%>
        <%--                <div class="entete">--%>
        <%--                    <h3>10:12AM, Today</h3>--%>
        <%--                    <h2>Vincent</h2>--%>
        <%--                    <span class="status blue"></span>--%>
        <%--                </div>--%>
        <%--                <div class="triangle"></div>--%>
        <%--                <div class="message">--%>
        <%--                    OK--%>
        <%--                </div>--%>
        <%--            </li>--%>
        <%--            <li class="you">--%>
        <%--                <div class="entete">--%>
        <%--                    <span class="status green"></span>--%>
        <%--                    <h2>Vincent</h2>--%>
        <%--                    <h3>10:12AM, Today</h3>--%>
        <%--                </div>--%>
        <%--                <div class="triangle"></div>--%>
        <%--                <div class="message">--%>
        <%--                    Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor.--%>
        <%--                </div>--%>
        <%--            </li>--%>
        <%--            <li class="me">--%>
        <%--                <div class="entete">--%>
        <%--                    <h3>10:12AM, Today</h3>--%>
        <%--                    <h2>Vincent</h2>--%>
        <%--                    <span class="status blue"></span>--%>
        <%--                </div>--%>
        <%--                <div class="triangle"></div>--%>
        <%--                <div class="message">--%>
        <%--                    Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor.--%>
        <%--                </div>--%>
        <%--            </li>--%>
        <%--            <li class="me">--%>
        <%--                <div class="entete">--%>
        <%--                    <h3>10:12AM, Today</h3>--%>
        <%--                    <h2>Vincent</h2>--%>
        <%--                    <span class="status blue"></span>--%>
        <%--                </div>--%>
        <%--                <div class="triangle"></div>--%>
        <%--                <div class="message">--%>
        <%--                    OK--%>
        <%--                </div>--%>
        <%--            </li>--%>
    </ul>
    <footer>
        <textarea name="sendMsg" id="messageInput" placeholder="Enter message"></textarea>
        <div class="btn-container">
            <div class="left">
                <button onclick="sendMessage();" class="match">매칭완료</button>
            </div>
            <div class="right">
                <button onclick="sendMessage();" class="send">전송</button>
            </div>
        </div>
    </footer>
</main>
</div>

</div>
<div class="chat-close">
    <button onclick="closeSocket();">Close Socket</button>
</div>
<br/>
</div>

<script type="text/javascript">
    var websocket;
    var inputMessage = document.getElementById('messageInput');
    var sessionNickname = '<%=session.getAttribute("sessionNickname")%>';

    document.addEventListener("DOMContentLoaded", function () {
        if (sessionNickname && sessionNickname.trim() !== "") {
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