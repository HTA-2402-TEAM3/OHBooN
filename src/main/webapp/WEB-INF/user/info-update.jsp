
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<%@ include file="../include/header.jsp" %>

<div class="container">
    <h2 class ="mt-5 mb-5"> 사용자 정보</h2>
    <form action="${pageContext.request.contextPath}/user/info/update" method="post" enctype="multipart/form-data">
        <table class="table table-bordered">
            <tr>
                <th>항목</th>
                <th>기존의 내용</th>
                <th>변경될 내용</th>
            </tr>
            <tr>
                <th>PROFILE IMAGE</th>
                <td>
                    <c:choose>
                        <c:when test="${not empty infoUserDto.profile}">
                            <img src="${request.contextPath}/upload/${infoUserDto.profile}" class="profile">
                        </c:when>
                        <c:otherwise>
                            이미지없음
                        </c:otherwise>
                    </c:choose>
                </td>

                <td>
                    <label for="profile" class="form-label">PROFILE</label>
                    <input class="form-control" type="file" id="profile" name="profile" accept=".gif, .jpg, .png">
                </td>

            </tr>
            <tr>
                <th>MEMBER GRADE</th>
                <td>${infoUserDto.grade}</td>
                <td>-</td>
            </tr>
            <tr>
                <th>User EMAIL</th>
                <td>${infoUserDto.email}</td>
                <td>이메일 변경은 고객센터에 문의하세요</td>
            </tr>
            <tr>
                <th>USER NICKNAME</th>
                <td>${infoUserDto.nickname}</td>
                <td>닉네임 변경은 고객센터에 문의하세요</td>
            </tr>
            <tr>
                <th>USER NAME</th>
                <td>${infoUserDto.username}</td>
                <td>개명사항은 고객센터에 문의하세요</td>
            </tr>
            <tr>
                <th>USER ACCOUNT CREATED DATE</th>
                <td>${infoUserDto.createDate}</td>
                <td>-</td>
            </tr>
            <tr>
                <th>USER PHONE NUMBER</th>
                <td>${infoUserDto.phone}</td>
                <td><input type="text" class="form-control" id="phone" placeholder="user phone number" name="phone"></td>
            </tr>
            <tr>
                <th>USER BIRTH DATE</th>
                <td>${infoUserDto.birth}</td>
                <td>생년월일 정정은 고객센터에 문의하세요</td>
            </tr>
            <tr>
                <th>EVALUATION</th>
                <td>${infoUserDto.evaluation}</td>
                <td>-</td>
            </tr>
            <tr>
                <th>AGREEMENT OF OFFERING INFORMATION</th>
                <td>${infoUserDto.agreeInfoOffer?"동의":"미동의"}</td>
                <td><input type="checkbox" class="form-check" name="agreeInfoOffer" ${infoUserDto.agreeInfoOffer?"checked":""}></td>
            </tr>
        </table>

        <<div>
            <a href="../user/password-update" class="btn btn-dark">비밀번호 변경</a>
            <button type="submit" class="btn btn-primary">변경사항 저장</button>
        </div>

    </form>
</div>


<script>
    console.log(${infoUserDto.agreeInfoOffer})
</script>

<%@ include file="../include/footer.jsp" %>
