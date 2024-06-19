
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<%@ include file="../include/header.jsp" %>

<div class="container">
    <h2 class ="mt-5 mb-5"> 사용자 정보</h2>
    <table class="table table-bordered">
        <tr>
            <th>PROFILE</th>
            <td>
                <th>${infoUserDto.email}</th>
                <c:choose>
                    <c:when test="${not empty infoUserDto.profile}">
                        <img src="${request.contextPath}/upload/${infoUserDto.profile}" class="profile">
                    </c:when>
                    <c:otherwise>
                        <img src="../images/user.png" class="profile">
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
        <tr>
            <th>MEMBER GRADE</th>
            <th>${infoUserDto.grade}</th>
        </tr>
        <tr>
            <th>User EMAIL</th>
            <th>${infoUserDto.email}</th>
        </tr>
        <tr>
            <th>USER NICKNAME</th>
            <th>${infoUserDto.nickname}</th>
        </tr>
        <tr>
            <th>USER NAME</th>
            <th>${infoUserDto.username}</th>
        </tr>
        <tr>
            <th>BIRTH DATE</th>
            <th>${infoUserDto.birth}</th>
        </tr>
        <tr>
            <th>USER PHONE NUMBER</th>
            <th>${infoUserDto.phone}</th>
        </tr>
        <tr>
            <th>USER BIRTH DATE</th>
            <th>${infoUserDto.birth}</th>
        </tr>
        <tr>
            <th>EVALUATION</th>
            <th>${infoUserDto.evaluation}</th>
        </tr>
        <tr>
            <th>AGREEMENT OF OFFERING INFORMATION</th>
            <th>${infoUserDto.agreeInfoOffer?"동의":"미동의"}</th>
        </tr>
    </table>

    <div>
        <a href="../user/password-confirm" class="btn btn-dark">회원정보 변경</a>
    </div>

</div>

<%@ include file="../include/footer.jsp" %>
