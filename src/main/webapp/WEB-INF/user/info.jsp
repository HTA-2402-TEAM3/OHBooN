
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<%@ include file="../include/header.jsp" %>

<div class="container">
    <h2 class ="mt-5 mb-5"> 사용자 정보</h2>
    <table class="table table-bordered">
        <tr>
            <th>항목</th>
            <th>내용</th>
            <th>기타</th>
        </tr>
        <tr>
            <th>PROFILE IMAGE</th>
            <td>
                <c:choose>
                    <c:when test="${not empty infoUserDto.profile}">
                        <img src="${request.contextPath}/upload/${infoUserDto.profile}" class="profile">
                    </c:when>
                    <c:otherwise>
                        <img src="../images/user.png" class="profile"> 이미지없음
                    </c:otherwise>
                </c:choose>
            </td>
            <td></td>
        </tr>
        <tr>
            <th>MEMBER GRADE</th>
            <td>${infoUserDto.grade}</td>
            <td></td>
        </tr>
        <tr>
            <th>User EMAIL</th>
            <td>${infoUserDto.email}</td>
            <td></td>
        </tr>
        <tr>
            <th>USER NICKNAME</th>
            <td>${infoUserDto.nickname}</td>
            <td></td>
        </tr>
        <tr>
            <th>USER NAME</th>
            <td>${infoUserDto.userName}</td>
            <td></td>
        </tr>
        <tr>
            <th>BIRTH DATE</th>
            <td>${infoUserDto.birth}</td>
            <td></td>
        </tr>
        <tr>
            <th>USER PHONE NUMBER</th>
            <td>${infoUserDto.phone}</td>
            <td></td>
        </tr>
        <tr>
            <th>USER ACCOUNT CREATED DATE</th>
            <td>${infoUserDto.createDate}</td>
            <td></td>
        </tr>
        <tr>
            <th>EVALUATION</th>
            <td>${infoUserDto.evaluation}</td>
            <td></td>
        </tr>
        <tr>
            <th>AGREEMENT OF OFFERING INFORMATION</th>
            <td>${infoUserDto.agreeInfoOffer?"동의":"미동의"}</td>
            <td></td>
        </tr>
    </table>

    <div>
        <a href="${pageContext.request.contextPath}/user/info/password-confirm" class="btn btn-dark">회원정보 변경</a>
    </div>
</div>


<%@ include file="../include/footer.jsp" %>
