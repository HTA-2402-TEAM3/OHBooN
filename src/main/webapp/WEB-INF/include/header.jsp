<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<style>
    .profile {
        max-width: 100px;  /* 원하는 최대 너비로 설정 */
        max-height: 100px;  /* 원하는 최대 높이로 설정 */
        width: auto;
        height: auto;
    }
    #preview{
        max-width: 100px;  /* 원하는 최대 너비로 설정 */
        max-height: 100px;  /* 원하는 최대 높이로 설정 */
        width: auto;
        height: auto;
    }

    /* 테이블 스타일 */
    table {
        border-collapse: collapse; /* 셀 경계선을 병합하여 단일 경계선으로 표시 */
        width: 100%; /* 테이블 너비를 전체로 설정 */
    }

    /* 테이블 헤더 스타일 */
    th, td {
        border: 1px solid #000; /* 셀 경계선을 검은색으로 설정 */
        padding: 2px; /* 셀 내부 여백을 2px로 설정 */
        text-align: left; /* 텍스트를 왼쪽 정렬 */
    }

    /* 테이블 헤더 스타일 */
    th {
        background-color: #f2f2f2; /* 헤더 배경색을 밝은 회색으로 설정 */
    }

</style>

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
                    <li><a href="/admin/home" class="btn btn-outline-info mx-1">ADMIN HOME</a></li>
                    <li><a href="/admin/userList" class="btn btn-outline-info mx-1">USER LIST</a></li>
                </c:when>
            </c:choose>

            <li><a href="../index/index" class="nav-link px-2 link-secondary">Home</a></li>
            <li><a href="../board/list" class="nav-link px-2">Board</a></li>


            <li><a href="../board/list?page=1" class="nav-link px-2">Board</a></li>
            <li><a href="../chat" class="nav-link px-2">Chat</a></li>

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
                                    <a href="/user/info?nickname=${sessionNickname}" class="d-block">
                                        <img src="${pageContext.request.contextPath}/upload/${sessionScope.sessionProfile}" class="profile">
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <a href="/user/info?nickname=${sessionNickname}" class="d-block">
                                        <img src="../images/user.png" class="profile">
                                    </a>
                                </c:otherwise>
                            </c:choose>
                            <a href="/user/info?nickname=${sessionNickname}" class="d-block">
                                <span> ${sessionNickname}</span>
                            </a>
                            <span> 님</span>
                        </div>
                    </div>
                    <a href="/user/logout" class="btn btn-primary mx-2">LOGOUT</a>
                </div>
            </c:otherwise>
        </c:choose>
    </header>
</div>