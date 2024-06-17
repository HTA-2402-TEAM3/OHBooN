<%--
  Created by IntelliJ IDEA.
  User: Dayou
  Date: 2024-06-17
  Time: 오후 2:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="form-floating">
    <input type="text" class="form-control" id="floatingInput" placeholder="User Email" name="email"
           value="${cookie.loggedEmail.value}">
    <label for="floatingInput">User Email </label>
</div>
<div class="form-floating">
    <input type="password" class="form-control" id="floatingPassword" placeholder="Password" name="userPW">
    <label for="floatingPassword">Password</label>
</div>
<button class="btn btn-primary w-100 py-2" type="submit">LOGIN</button>
</body>
</html>
