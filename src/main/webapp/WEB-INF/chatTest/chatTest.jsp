<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>WebSocket Test</title>
</head>
<body>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://code.jquery.com/jquery-latest.min.js"></script>

<h2>WebSocket test</h2>
<%--<button onclick="openSocket();">Open Socket</button>--%>
<button id="sendBtn" onclick="sendMessage();">Send Message</button>
<button id="closeBtn" onclick="closeSocket();">Close Socket</button>
<br/>
<input type="text" id="messageInput" placeholder="Enter message" name="sendMsg">
<br/>
<%--<c:if test="${user_id eq match_email}">--%>
<%--    <button onclick="matching(${match_id});">matching</button>--%>
<%--</c:if>--%>
<br/>
<span>

    ${msgList}
</span>

<div id="messages"></div>


</body>
<script type="text/javascript">
    var websocket;
    var inputMessage = document.getElementById('messageInput');

    document.addEventListener("DOMContentLoaded", function () {
        // function openSocket() {
        // writeResponse("WebSocket is open!!!!");
        // Ensure only one connection is open at a time
        if (websocket !== undefined && websocket.readyState !== WebSocket.CLOSED) {
            writeResponse("WebSocket is already opened.");
            return;
        }
        // Create a new instance of the websocket

        websocket = new WebSocket("ws://localhost:8080/chat");
        // websocket.onopen = function (event) {
        //     if (event.data === undefined) return;
        //     writeResponse(event.data);
        // };
        websocket.onmessage = function (event) {
            // ws객체에 전달받은 메세지가 있으면 실행되는 함수
            var message = event.data.split(":");
            // 보낼 때 id랑 같이 보냄. |로 구분
            var sender = message[0];
            // 보낸 사람 id
            var content = message[1];
            // msg
            console.log(message);
            var messages = document.getElementById("messages");
            messages.innerHTML += "<p class='chat_content'>" + sender + " : " + content + "</p>";

            // $("#messages").html($("#messages").html()
            //     + "<p class='chat_content'>" + sender + " : " + content + "</p>");
        };
        websocket.onclose = function (event) {
            writeResponse("Connection closed");
        };
        websocket.onerror = function (event) {
            console.log(event);
        }

    });

    function sendMessage() {
        var text = document.getElementById("messageInput").value;
        //ws 객체에 session id랑 msg보냄
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

    function matching() {
        $.ajax({
            method: 'post',
            url: '../match',
            data: {
                match_id: "${match_id}"
            },
            success : function (data) {
                alert(JSON.stringify(data));
                if(data.isMatch > 0) {
                    alert("매칭 확정 되었습니다.");
                } else {
                    alert("err");
                }
            },
            error:function (e) {
                console.log(e);
            }
        })
    }
</script>
</html>
