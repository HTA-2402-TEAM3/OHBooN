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
            <p class="card-text"><strong>글쓴이:</strong></p>
            <p class="card-text"><strong>작성일:</strong></p>
            <p class="card-text"><strong>위치 정보:</strong> ${board.location}</p>
        </div>
    </div>


    <form id="boardDeleteForm" action="../board/delete" method="post">

        <c:if test="${not empty param.boardID}">
            <input type="hidden" name="boardID" value="${param.boardID}">
        </c:if>
        <c:if test="${sessionScope.sessionEmail eq board.email}">
            <button type="button" id="btn-sign" class="btn btn-light">삭제</button>
        </c:if>

    </form>



    <button type="button" class="btn btn-light" onclick="window.history.back()">목록</button>



    <script>
        document.addEventListener('DOMContentLoaded', function () {
            document.getElementById("btn-sign").addEventListener('click', function () {

                let boardForm = document.getElementById("boardDeleteForm");
                let boardData = new FormData(boardForm);

                fetch('/board/delete', {
                    method: 'POST',
                    body: boardData
                })
                    .then(resp => {
                        if (!resp.ok) {
                            return resp.json().then(error => {
                                throw new Error(error.message);
                            });
                        }
                        return resp.json();
                    })
                    .then(data => {
                        console.log(data.message);
                        alert(data.deleteMessage)
                        location.href = "/board/list"
                    })
                    .catch(error => {
                        console.log(error);
                        alert(error.message);
                    })
            });
        });
    </script>
</div>

<%@ include file="../include/footer.jsp" %>