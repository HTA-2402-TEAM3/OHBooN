<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>WebSocket Test</title>
  ${chatRoomDto}
</head>
<body>
<%=session.getId()%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

<h2>WebSocket test</h2>
<button onclick="openSocket();">Open Socket</button>
<button onclick="sendMessage();">Send Message</button>
<button onclick="closeSocket();">Close Socket</button>
<%--<select class="form-select" multiple aria-label="Multiple select example">--%>
<%--  <option selected>Open this select menu</option>--%>
<%--  <c:forEach items="${chatRoomList}" var="chatRoomDto" varStatus="loop">--%>
<%--    <option value="${chatRoomList.chat_id}">${chatRoomList.subject}</option>--%>
<%--  </c:forEach>--%>
<%--  &lt;%&ndash;    <option value="1">One</option>&ndash;%&gt;--%>
<%--  &lt;%&ndash;    <option value="2">Two</option>&ndash;%&gt;--%>
<%--  &lt;%&ndash;    <option value="3">Three</option>&ndash;%&gt;--%>
<%--</select>--%>
<br/>
<input type="text" id="messageInput" placeholder="Enter message" name="sendMsg">
<div id="messages"></div>
</body>
<script type="text/javascript">
  var websocket;
  var inputMessage = document.getElementById('messageInput');

  function openSocket() {
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

  }

  function sendMessage() {
    var text = document.getElementById("messageInput").value;
    //ws 객체에 session id랑 msg보냄

    var message = {
      match_id: "${match_id}",
      chat_id: "${chat_id}",
      sender: "<%=session.getId()%>",
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

</html>
