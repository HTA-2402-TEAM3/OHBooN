<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<!DOCTYPE html>

<html>
<head>
    <title>JSP - Hello World</title>
    <link href="../../css/bootstrap.min.css" rel="stylesheet">
    <link href="../../css/common.css" rel="stylesheet">
    <link href="../../css/sign-in.css" rel="stylesheet">
    <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
    <script src="../../js/bootstrap.bundle.min.js"></script>
    <script src="../../js/jquery-3.7.1.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
      <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

</head>
<body>
<div class="container">
    <header class="d-flex flex-wrap align-items-center justify-content-center justify-content-md-between py-3 mb-4 border-bottom">
        <div class="col-md-3 mb-2 mb-md-0">
            <a href="" class="d-inline-flex link-body-emphasis text-decoration-none">
                <svg class="bi" width="40" height="32" role="img" aria-label="Bootstrap">
                    <use xlink:href="#bootstrap"></use>
                </svg>
            </a>
        </div>
        <ul class="nav col-12 col-md-auto mb-2 justify-content-center mb-md-0">

            <c:choose>
                <c:when test="${sessionGrade eq 'ADMIN' or sessionGrade eq 'MANAGER'}">
                    <li><a href="../admin/home" class="btn btn-outline-info">ADMIN HOME</a></li>
                    <li><a href="../admin/userList" class="btn btn-outline-info">USER LIST</a></li>
                </c:when>
            </c:choose>

            <li><a href="../index/index" class="nav-link px-2 link-secondary">Home</a></li>


            <li><a href="../board/list?page=1" class="nav-link px-2">Board</a></li>
            <li><a href="../chat" class="nav-link px-2">Chat</a></li>

            <li><a href="" class="nav-link px-2">FAQs</a></li>
        </ul>
        <c:choose>
            <c:when test="${sessionEmail eq null}">
                <div class="col-md-3 text-end">
                    <a href="/user/login" class="btn btn-outline-primary me-2">Login</a>
                    <a href="/user/signup" class="btn btn-primary">Sign-up</a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="col-md-3 text-end d-flex align-items-center">
                    <div class="d-inline-block">

                        <div class="col-md-3 text-end d-flex align-items-center">
                            <c:choose>
                                <c:when test="${not empty sessionScope.sessionProfile}">
                                    <a href="../user/info?nickname=${sessionNickname}" class="d-block" >
                                        <img src="${pageContext.request.contextPath}/upload/${sessionScope.sessionProfile}" class="profile">
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <a href="../user/info?nickname=${sessionNickname}" class="d-block">
                                        <img src="../images/user.png" class="profile" >
                                    </a>
                                </c:otherwise>
                            </c:choose>

                            <a href="../user/info?nickname=${sessionNickname}" class="d-block">
                                <span> ${sessionNickname}</span>
                            </a>
                            <span> 님</span>
                        </div>
                    </div>
                    <a href="../user/logout" class="btn btn-primary mx-2">LOGOUT</a>
                </div>
            </c:otherwise>
        </c:choose>
    </header>
</div>
