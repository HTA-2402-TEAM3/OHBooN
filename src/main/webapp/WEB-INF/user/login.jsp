
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<%@ include file="../include/header.jsp" %>

<main class="form-signin w-100 m-auto">
    <form action="../user/login" method="post">

        <h2 class="h3 mb-3 fw-normal">LOGIN</h2>

        <div class="form-floating">
            <input type="text" class="form-control" id="floatingInput" placeholder="User Email" name="email"
                   value="${cookie.loggedEmail.value}">
            <label for="floatingInput">User Email </label>
        </div>
        <div class="form-floating">
            <input type="password" class="form-control" id="floatingPassword" placeholder="Password" name="userPW">
            <label for="floatingPassword">Password</label>
        </div>

        <div class="form-check text-start my-3">
            <input class="form-check-input" type="checkbox" id="flexCheckDefault" name="saveID"
                   value="yes" <c:if test="${cookie.loggedEmail.value != null}">checked</c:if> >
            <label class="form-check-label" for="flexCheckDefault">
                Remember me
            </label>
        </div>
        <button class="btn btn-primary w-100 py-2" type="submit">LOGIN</button>
        <a href="../user/password-search" class="btn btn-outline-secondary w-100 py-2 mt-2">LOST PASSWORD</a>
    </form>
</main>

<%@ include file="../include/footer.jsp" %>
