<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="../include/header.jsp" %>
<div class="container mt-4">
    <h1 class="mb-4">게시글 보기</h1>

    <div class="card">
        <div class="card-header">
            <h2>제목</h2>
        </div>
        <div class="card-body">
            <p class="card-text"><strong>내용:</strong> ${board.content}</p>
            <p class="card-text"><strong>글쓴이:</strong> </p>
            <p class="card-text"><strong>작성일:</strong> </p>
            <p class="card-text"><strong>위치 정보:</strong> ${board.location}</p>
        </div>
    </div>


</div>

<%@ include file="../include/footer.jsp" %>