<%--
  Created by IntelliJ IDEA.
  User: Dayou
  Date: 2024-06-20
  Time: 오후 3:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
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
</body>
</html>
