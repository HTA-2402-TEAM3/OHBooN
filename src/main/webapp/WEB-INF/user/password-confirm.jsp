<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<%@ include file="../include/header.jsp" %>

<div class="container">
    <h2>비밀번호 확인</h2>
    <form action="${pageContext.request.contextPath}/user/password-check" method="post">
        <div class="mb-3">
            <label for="password" class="form-label">비밀번호</label>
            <input type="password" class="form-control" id="password" name="password" required>
        </div>
        <button type="submit" class="btn btn-primary">확인</button>
    </form>
</div>

<%@ include file="../include/footer.jsp" %>