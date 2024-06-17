<%--
  Created by IntelliJ IDEA.
  User: Dayou
  Date: 2024-06-13
  Time: 오전 9:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>채팅</title>
</head>
<body>
<form action="makeChat" method="post">
    <input type="text" name="board_id" placeholder="board_id">
    <button>채팅방 생성</button>
</form>

<form action="enterChat" method="get">
    <input type="text" name=chat_id placeholder="chat_id">
    <input type="text" name=user_id placeholder="user_id">
    <button>채팅방 입장</button>
</form>

<div id="messages"></div>

<script>
    var websocket;
    var inputMessage = document.getElementById('messageInput');

    document.addEventListener("DOMContentLoaded", function () {
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
    });
</script>
</body>
</html>