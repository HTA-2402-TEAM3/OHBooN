<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>닉네임 변경</title>
    <link rel="stylesheet" href="<c:url value='/css/common.css' />">
</head>
<body>
<div class="container">
    <h2>닉네임 변경</h2>
    <form id="nicknameForm" action="${pageContext.request.contextPath}/admin/updateNickname" method="post">
        <input type="hidden" name="email" value="${email}">
        <div class="mb-3">
            <label for="currentNickname" class="form-label">현재 닉네임</label>
            <input type="text" class="form-control" id="currentNickname" value="${nickname}" readonly />
        </div>
        <div class="form-group">
            <label for="newNickname">새 닉네임:</label>
            <input type="text" id="newNickname" name="newNickname" class="form-control" required>
        </div>
        <button type="button" class="btn btn-primary" onclick="checkAndSubmitNickname()">변경하기</button>
    </form>
</div>

<script>
    function checkAndSubmitNickname() {
        const newNickname = document.getElementById('newNickname').value;
        if (!newNickname) {
            alert('새 닉네임을 입력해주세요.');
            return;
        }

        fetch('${pageContext.request.contextPath}/admin/nicknameCheck?nickname=' + encodeURIComponent(newNickname))
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                if (data.exists) {
                    alert('이미 존재하는 닉네임입니다.');
                } else {
                    if (confirm('${nickname} 을(를) ' + newNickname + '으로 변경하시겠습니까?')) {
                        document.getElementById('nicknameForm').submit();
                    }
                }
            })
            .catch(error => {
                console.error('There was a problem with the fetch operation:', error);
                alert('닉네임 확인 중 오류가 발생했습니다.');
            });
    }
</script>
</body>
</html>
