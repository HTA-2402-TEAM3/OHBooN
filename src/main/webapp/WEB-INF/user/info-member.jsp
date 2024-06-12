
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<%@ include file="../include/header.jsp" %>

<div class="container">
    <h2 class ="mt-5 mb-5"> 회원정보</h2>
    <table class="table table-bordered">
        <tr>
            <th>PROFILE</th>
            <td>
                <c:choose>
                    <c:when test="${not empty infoMemberDto.renameProfile}">
                        <img src="${request.contextPath}/upload/${infoMemberDto.renameProfile}" class="profile">
                    </c:when>
                    <c:otherwise>
                        <img src="../images/user.png" class="profile">
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
        <tr>
            <th>USER ID</th>
            <th>${infoMemberDto.userID}</th>
        </tr>
        <tr>
            <th>USER NAME</th>
            <th>${infoMemberDto.userName}</th>
        </tr>
        <tr>
            <th>E-MAIL</th>
            <th>${infoMemberDto.userEmail}</th>
        </tr>
        <tr>
            <th>POST CODE</th>
            <th>${infoMemberDto.postCode}</th>
        </tr>
        <tr>
            <th>ADDRESS</th>
            <td>${infoMemberDto.address} <br>${infoMemberDto.addressDetail}</td>
        </tr>
        <tr>
            <th>USER BIRTH DATE</th>
            <th>${infoMemberDto.birth}</th>
        </tr>
    </table>
    <div>
        <a href="../user/password-change" class="btn btn-dark">비밀번호 변경</a>
        <%--page 만들고 기존 비밀번호, 변경할 비밀번호, 변경할 비밀번호 확인 을 입력하는 창을 만들어 비밀번호 바꾸기--%>
    </div>
</div>

<%@ include file="../include/footer.jsp" %>
