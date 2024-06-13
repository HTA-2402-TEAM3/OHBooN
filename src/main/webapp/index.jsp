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

<form action="startChat" method="get">
    <input type="text" name=chat_id placeholder="chat_id">
    <button>채팅방 입장</button>
</form>
</body>
</html>