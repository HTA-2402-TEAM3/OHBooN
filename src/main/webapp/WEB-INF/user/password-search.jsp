<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<%@ include file="../include/header.jsp" %>


<form action="../user/password-search" method="post">

    <div class="mb-3">
        <div class="row g-3">
            <div class="col-auto">
                <label for="email" class="form-label">USER E-MAIL: </label>
                <input type="email" class="form-control" id="email" placeholder="user email" name="email">
                <button type="button" id="btn-check-email" class="btn btn-dark mt-2">CHECK EMAIL</button>
                <button type="submit" id="btn-send-email" class="btn btn-outline-primary mt-2" value="비밀번호 재설정 링크 보내기">SEND MAIL TO CHANGE PW</button>
            </div>
        </div>
    </div>
</form>

<%@ include file="../include/footer.jsp" %>
