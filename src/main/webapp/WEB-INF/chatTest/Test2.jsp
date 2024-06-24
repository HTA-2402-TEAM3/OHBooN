<%-- Created by IntelliJ IDEA. User: Dayou Date: 2024-06-13 Time: 오전 9:16 --%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@include file="../include/header.jsp"%>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>채팅</title>
    <link rel="stylesheet" href="css/test2.css">
    <script src="https://kit.fontawesome.com/a2e8ca0ae3.js" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body>
<input type="hidden" id="chat_id" name="chat_id" value="${chat_id}" />
<%--채팅리스트--%>
<div id="container">
    <aside id="chatRoomList">
    </aside>
    <main>
        <ul id="messages">
        </ul>
        <footer>
            <textarea name="sendMsg" id="messageInput" placeholder="Enter message"></textarea>
            <div class="btn-container">
                <div class="left">
                    <button onclick="matching();" class="match">매칭완료</button>
                </div>
                <div class="right">
                    <button onclick="sendMessage();" class="send">전송</button>
                </div>
            </div>
        </footer>
    </main>
</div>
<script src="../js/Chat.js"></script>
</body>
</html>
<%@include file="../include/footer.jsp"%>
