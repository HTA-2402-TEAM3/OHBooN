<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="../include/header.jsp" %>


<div class="container mt-5">
    <h2>관리자 - 사용자 목록</h2>
    <p>Session Grade :  <%=session.getAttribute("sessionGrade")%>
    </p>

    <form action="${pageContext.request.contextPath}/admin/userList" method="get">
        <label for="limit">한 페이지에 표시할 사용자 수:</label>
        <select name="limit" id="limit" onchange="this.form.submit()">
            <option value="10" ${param.limit == '10' ? 'selected' : ''}>10명</option>
            <option value="20" ${param.limit == '20' ? 'selected' : ''}>20명</option>
            <option value="50" ${param.limit == '50' ? 'selected' : ''}>50명</option>
            <option value="100" ${param.limit == '100' ? 'selected' : ''}>100명</option>
            <option value="all" ${param.limit == 'all' ? 'selected' : ''}>최대</option>
        </select>
        <span> <p>* 주소 뒤에 limit= N 에서 N에 원하는 숫자 입력 가능</p></span>

        <!-- 검색 범위 선택 -->
        <label for="searchField">검색 범위:</label>
        <select name="searchField" id="searchField">
            <option value="all" ${param.searchField == 'all' ? 'selected' : ''}>전체</option>
            <option value="email" ${param.searchField == 'email' ? 'selected' : ''}>이메일</option>
            <option value="nickname" ${param.searchField == 'nickname' ? 'selected' : ''}>닉네임</option>
            <option value="profile" ${param.searchField == 'profile' ? 'selected' : ''}>프로필사진</option>
            <option value="evaluation" ${param.searchField == 'evaluation' ? 'selected' : ''}>평가점수</option>
            <option value="createdate" ${param.searchField == 'createdate' ? 'selected' : ''}>계정생성일</option>
            <option value="username" ${param.searchField == 'username' ? 'selected' : ''}>사용자이름</option>
            <option value="birth" ${param.searchField == 'birth' ? 'selected' : ''}>생년월일</option>
            <option value="phone" ${param.searchField == 'phone' ? 'selected' : ''}>연락처</option>
        </select>

        <!-- 검색 기능 -->
        <input type="text" name="searchKeyword" placeholder="검색어 입력" value="${param.searchKeyword}">
        <button type="submit">검색</button>
        <p>검색결과: ${totalCount} 건 </p>
    </form>

    <%--페이징 기능--%>
    <jsp:include page="pagination.jsp">
        <jsp:param name="currentPage" value="${currentPage}"/>
        <jsp:param name="totalPages" value="${totalPages}"/>
        <jsp:param name="limit" value="${'all'.equals(limit) ? 'all' : limit}"/>
        <jsp:param name="pageGroupSize" value="${pageGroupSize}"/>
        <jsp:param name="groupStartPage" value="${groupStartPage}"/>
        <jsp:param name="groupEndPage" value="${groupEndPage}"/>
        <jsp:param name="currentPageGroup" value="${currentPageGroup}"/>
        <jsp:param name="searchKeyword" value="${param.searchKeyword}"/>
        <jsp:param name="searchField" value="${param.searchField}"/>
        <jsp:param name="sortField" value="${param.sortField}"/>
        <jsp:param name="sortOrder" value="${param.sortOrder}"/>
    </jsp:include>

    <%-- 테이블 상단 스크롤바 --%>
    <div class="table-responsive" style="overflow-x: auto; position: relative;">
        <div id="top-scroll" style="height: 20px; overflow-x: auto; overflow-y: hidden;">
            <div <c:if test="${sessionGrade eq 'ADMIN'}">style="width: 250%">
            </c:if>">
        </div> <!-- 테이블의 넓이와 맞춰줌 -->
    </div>
</div>

<form action="${pageContext.request.contextPath}/admin/updateUserInfo" method="post">
    <input type="hidden" name="email" value="${user.email}"/>

    <div class="table-responsive" id="bottom-scroll">
        <table class="user-table"
               <c:if test="${sessionGrade eq 'ADMIN'}">style="width: 250%"</c:if> >

            <colgroup>
                <col style="width: auto;">

            </colgroup>

            <thead>
            <tr>
                <th>순서</th>
                <th>Contact</th>
                <th>
                    <a href="${pageContext.request.contextPath}/admin/userList?page=${currentPage}&limit=${limit}&searchField=${param.searchField == '' ? 'all' : param.searchField}&searchKeyword=${param.searchKeyword == '' ? '' : param.searchKeyword}&sortField=email&sortOrder=${param.sortOrder == 'asc' ? 'desc' : 'asc'}">1.Email</a>
                </th>
                <th>
                    <a href="${pageContext.request.contextPath}/admin/userList?page=${currentPage}&limit=${limit}&searchField=${param.searchField == '' ? 'all' : param.searchField}&searchKeyword=${param.searchKeyword == '' ? '' : param.searchKeyword}&sortField=nickname&sortOrder=${param.sortOrder == 'asc' ? 'desc' : 'asc'}">2.닉네임</a>
                </th>
                <th>
                    <a href="${pageContext.request.contextPath}/admin/userList?page=${currentPage}&limit=${limit}&searchField=${param.searchField == '' ? 'all' : param.searchField}&searchKeyword=${param.searchKeyword == '' ? '' : param.searchKeyword}&sortField=profile&sortOrder=${param.sortOrder == 'asc' ? 'desc' : 'asc'}">3.프로필사진</a>
                </th>
                <th>
                    <a href="${pageContext.request.contextPath}/admin/userList?page=${currentPage}&limit=${limit}&searchField=${param.searchField == '' ? 'all' : param.searchField}&searchKeyword=${param.searchKeyword == '' ? '' : param.searchKeyword}&sortField=privateField&sortOrder=${param.sortOrder == 'asc' ? 'desc' : 'asc'}">4.닉네임&프로필</a>
                </th>
                <th>
                    <a href="${pageContext.request.contextPath}/admin/userList?page=${currentPage}&limit=${limit}&searchField=${param.searchField == '' ? 'all' : param.searchField}&searchKeyword=${param.searchKeyword == '' ? '' : param.searchKeyword}&sortField=grade&sortOrder=${param.sortOrder == 'asc' ? 'desc' : 'asc'}">5.회원등급</a>
                </th>
                <th>
                    <a href="${pageContext.request.contextPath}/admin/userList?page=${currentPage}&limit=${limit}&searchField=${param.searchField == '' ? 'all' : param.searchField}&searchKeyword=${param.searchKeyword == '' ? '' : param.searchKeyword}&sortField=evaluation&sortOrder=${param.sortOrder == 'asc' ? 'desc' : 'asc'}">6.평가점수</a>
                </th>
                <th>
                    <a href="${pageContext.request.contextPath}/admin/userList?page=${currentPage}&limit=${limit}&searchField=${param.searchField == '' ? 'all' : param.searchField}&searchKeyword=${param.searchKeyword == '' ? '' : param.searchKeyword}&sortField=createdate&sortOrder=${param.sortOrder == 'asc' ? 'desc' : 'asc'}">7.계정생성일</a>
                </th>
                <th>
                    <a href="${pageContext.request.contextPath}/admin/userList?page=${currentPage}&limit=${limit}&searchField=${param.searchField == '' ? 'all' : param.searchField}&searchKeyword=${param.searchKeyword == '' ? '' : param.searchKeyword}&sortField=agreeInfoOffer&sortOrder=${param.sortOrder == 'asc' ? 'desc' : 'asc'}">8.정보제공 동의</a>
                </th>
                <c:choose>
                    <c:when test="${sessionGrade eq 'ADMIN'}">
                        <th>
                            <a href="${pageContext.request.contextPath}/admin/userList?page=${currentPage}&limit=${limit}&searchField=${param.searchField == '' ? 'all' : param.searchField}&searchKeyword=${param.searchKeyword == '' ? '' : param.searchKeyword}&sortField=username&sortOrder=${param.sortOrder == 'asc' ? 'desc' : 'asc'}">9.사용자 이름</a>
                        </th>
                        <th>
                            <a href="${pageContext.request.contextPath}/admin/userList?page=${currentPage}&limit=${limit}&searchField=${param.searchField == '' ? 'all' : param.searchField}&searchKeyword=${param.searchKeyword == '' ? '' : param.searchKeyword}&sortField=birth&sortOrder=${param.sortOrder == 'asc' ? 'desc' : 'asc'}">10.생년월일</a>
                        </th>
                        <th>
                            <a href="${pageContext.request.contextPath}/admin/userList?page=${currentPage}&limit=${limit}&searchField=${param.searchField == '' ? 'all' : param.searchField}&searchKeyword=${param.searchKeyword == '' ? '' : param.searchKeyword}&sortField=phone&sortOrder=${param.sortOrder == 'asc' ? 'desc' : 'asc'}">11.연락처</a>
                        </th>
                        <th>
                            <a href="${pageContext.request.contextPath}/admin/userList?page=${currentPage}&limit=${limit}&searchField=${param.searchField == '' ? 'all' : param.searchField}&searchKeyword=${param.searchKeyword == '' ? '' : param.searchKeyword}&sortField=available&sortOrder=${param.sortOrder == 'asc' ? 'desc' : 'asc'}">12.계정활성여부</a>
                        </th>
                        <th>
                            <a href="${pageContext.request.contextPath}/admin/userList?page=${currentPage}&limit=${limit}&searchField=${param.searchField == '' ? 'all' : param.searchField}&searchKeyword=${param.searchKeyword == '' ? '' : param.searchKeyword}&sortField=password&sortOrder=${param.sortOrder == 'asc' ? 'desc' : 'asc'}">13.패스워드</a>
                        </th>
                        <th>
                            <a href="${pageContext.request.contextPath}/admin/userList?page=${currentPage}&limit=${limit}&searchField=${param.searchField == '' ? 'all' : param.searchField}&searchKeyword=${param.searchKeyword == '' ? '' : param.searchKeyword}&sortField=requestTimeForDeletion&sortOrder=${param.sortOrder == 'asc' ? 'desc' : 'asc'}">14.탈퇴신청일시</a>
                        </th>
                        <th>
                            <a href="${pageContext.request.contextPath}/admin/userList?page=${currentPage}&limit=${limit}&searchField=${param.searchField == '' ? 'all' : param.searchField}&searchKeyword=${param.searchKeyword == '' ? '' : param.searchKeyword}&sortField=verificationCode&sortOrder=${param.sortOrder == 'asc' ? 'desc' : 'asc'}">15.인증코드</a>
                        </th>
                    </c:when>
                    <c:otherwise>
                        <th>기타 개인정보</th>
                    </c:otherwise>
                </c:choose>
                <th>변경사항 저장</th>
            </tr>
            </thead>

            <tbody>
            <c:choose>
                <c:when test="${not empty userList}">
                    <c:forEach var="user" items="${userList}" varStatus="status">
                        <tr>
                            <td>${status.index + 1}</td>
                            <!-- 순서 추가 한 페이지에 들어가는 숫자에 맞게 기입: 예) 1~10 / 1~20 / 1~50 / 1~최대-->

                                <%--사용자에게 1:1 메시지 발송 : @WebServlet("/makeUserChat") 페이지로 연결. 사용자는 이메일 값으로 탐색--%>
                            <td>
                                <button type="button" onclick="sendMessage('${user.email}')">Message</button>
                            </td>

                                <%--1. 사용자 이메일--%>
                            <td>${user.email}</td>

                                <%--2. 사용자 닉네임--%>
                            <td>
                                <c:choose>
                                    <c:when test="${sessionGrade eq 'ADMIN'}">
                                        <%-- 닉네임 수정페이지 띄우기 이동 --%>
                                        <a href="javascript:void(0);"
                                           onclick="openUpdateNicknamePopup('${user.email}', '${user.nickname}')"
                                           class="btn btn-warning">${user.nickname}</a>
                                    </c:when>
                                    <c:when test="${sessionGrade eq 'MANAGER'}">
                                        ${user.nickname}
                                    </c:when>
                                </c:choose>
                            </td>

                                <%--3. 사용자 프로필사진--%>
                            <td>
                                <c:choose>
                                    <c:when test="${empty user.profile}">(이미지 없음)</c:when>
                                    <c:otherwise>
                                        <%--코드 통합 후 이미지 파일 저장 경로 세팅 필요.--%>
                                        <a href="${pageContext.request.contextPath}/upload/${user.profile}"
                                           target="_blank">
                                            <img src="${pageContext.request.contextPath}/upload/${user.profile}"
                                                 class="profile" alt="img: ${user.profile}">
                                        </a>
                                    </c:otherwise>
                                </c:choose>
                            </td>

                                <%--4. 사용자 아이디 및 프로필사진 비공개처리--%>
                            <td style="width:150px">
                                <div>
                                    <input type="radio" id="public_${user.email}" name="privateField_${user.email}"
                                           value="false" ${!user.privateField ? 'checked' : ''}>
                                    <label for="public_${user.email}">공개</label>

                                    <input type="radio" id="private_${user.email}" name="privateField_${user.email}"
                                           value="true" ${user.privateField ? 'checked' : ''}>
                                    <label for="private_${user.email}">비공개</label>
                                </div>
                            </td>

                            <td style="width:200px"> <%--5. 사용자 등급--%>
                                <div class="user-grade-container">
                                    <div class="current-grade">현재: ${user.grade}</div>
                                    <div class="form-group row align-items-center">
                                        <div class="col-auto"><label for="grade-select">변경:</label></div>
                                        <div class="col-auto"><select id="grade-select" name="grade"
                                                                      class="form-control">
                                            <option value="STANDBY" ${user.grade == 'STANDBY' ? 'selected' : ''}>
                                                STANDBY
                                            </option>
                                            <option value="MEMBER" ${user.grade == 'MEMBER' ? 'selected' : ''}>MEMBER
                                            </option>
                                            <option value="RESTRICTED1" ${user.grade == 'RESTRICTED1' ? 'selected' : ''}>
                                                RESTRICTED1
                                            </option>
                                            <option value="RESTRICTED2" ${user.grade == 'RESTRICTED2' ? 'selected' : ''}>
                                                RESTRICTED2
                                            </option>
                                            <option value="BANNED" ${user.grade == 'BANNED' ? 'selected' : ''}>BANNED
                                            </option>
                                        </select>
                                        </div>

                                    </div>
                                </div>
                            </td>

                            <td class="align-items-center">${user.evaluation}</td>
                                <%--6. 사용자 평가점수--%>
                            <td>${user.formattedCreateDate}</td>
                                <%--7. 사용자 계정생성일--%>
                            <td>${user.agreeInfoOffer ? "동의" : "미동의"}</td>
                                <%--8. 사용자 개인정보 제공 동의 여부(true/false)--%>
                            <c:choose>
                                <c:when test="${sessionGrade eq 'ADMIN'}">
                                    <td><%--9. 사용자 이름--%>
                                        <div class="form-group row align-items-center">
                                            <ul>
                                                <div class="col-auto">
                                                    <li>${user.userName}</li>
                                                </div>
                                                <div class="col-auto">
                                                    <li><input type="text" class="form-control" id="userName"
                                                               name="userName" value="${user.userName}" required></li>
                                                </div>
                                            </ul>
                                        </div>
                                    </td>
                                    <td><%--10. 사용자 생년월일--%>
                                        <div class="row align-items-center">
                                            <ul>
                                                <div class="col-auto">
                                                    <li>${user.birth}</li>
                                                </div>
                                                <div class="col-auto">
                                                    <input type="date" class="form-control" id="birth" name="birth"
                                                           value="${user.birth}" required>
                                                </div>
                                            </ul>
                                        </div>
                                    </td>
                                    <td style="width: 400px"> <%--11. 사용자 전화번호--%>
                                        <div class="row align-items-center">
                                            <ul>
                                                <div class="col-auto">${user.phone}</div>
                                                <div class="col-auto">
                                                    변경: <input type="text" class="form-control" id="phone" name="phone"
                                                               value="${user.phone}" required>
                                                </div>
                                            </ul>
                                        </div>
                                    </td>
                                    <td><%--12. 사용자 계정 활성여부--%>
                                        <div class="align-items-center">
                                            <input type="radio" id="available_${user.email}"
                                                   name="available_${user.email}"
                                                   value="true" ${user.available ? 'checked' : ''}>
                                            <label for="available_${user.email}">활성</label>

                                            <input type="radio" id="not_available_${user.email}"
                                                   name="available_${user.email}"
                                                   value="false" ${!user.available ? 'checked' : ''}>
                                            <label for="not_available_${user.email}">비활성</label>
                                        </div>
                                    </td>
                                    <td> <%--13. 사용자 패스워드--%>
                                        <c:choose>
                                            <c:when test="${user.userPW.startsWith('$2a$') or user.userPW.startsWith('$2b$') and user.userPW.length() == 60}">
                                                정상
                                            </c:when>
                                            <c:otherwise>
                                                ## 비정상PW ##
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>${user.requestTimeForDeletion}</td> <%--14. 사용자 계정 삭제요청시간--%>

                                    <td> <%--15. 계정 인증코드--%>
                                        <c:choose>
                                            <c:when test="${user.verificationCode eq null}">-</c:when>
                                            <c:otherwise>존재</c:otherwise>
                                        </c:choose>
                                    </td>
                                </c:when>
                                <c:otherwise>
                                    <td>열람불가</td>
                                </c:otherwise>
                            </c:choose>
                            <td>
                                <c:choose>
                                    <c:when test="${sessionGrade eq 'ADMIN'}">
                                        <button type="submit" class="btn btn-primary mt-1" id="admin-edit"
                                                name="admin-edit">업데이트
                                        </button>
                                    </c:when>
                                    <c:when test="${sessionGrade eq 'MANAGER'}">
                                        <button type="submit" class="btn btn-primary mt-1" id="mgr-edit"
                                                name="mgr-edit">
                                            업데이트
                                        </button>
                                    </c:when>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <tr>
                        <td colspan="17" class="text-left">검색 결과가 없습니다</td>
                    </tr>
                </c:otherwise>
            </c:choose>
            </tbody>
        </table>
    </div>
</form>

<%--페이징 기능--%>
<jsp:include page="pagination.jsp">
    <jsp:param name="currentPage" value="${currentPage}"/>
    <jsp:param name="totalPages" value="${totalPages}"/>
    <jsp:param name="limit" value="${'all'.equals(limit) ? 'all' : limit}"/>
    <jsp:param name="pageGroupSize" value="${pageGroupSize}"/>
    <jsp:param name="groupStartPage" value="${groupStartPage}"/>
    <jsp:param name="groupEndPage" value="${groupEndPage}"/>
    <jsp:param name="currentPageGroup" value="${currentPageGroup}"/>
    <jsp:param name="searchKeyword" value="${param.searchKeyword}"/>
    <jsp:param name="searchField" value="${param.searchField}"/>
    <jsp:param name="sortField" value="${param.sortField}"/>
    <jsp:param name="sortOrder" value="${param.sortOrder}"/>
</jsp:include>

</div>

<script>
    // 1:1 메시지 발송용 함수
    function sendMessage(email) {
        const form = document.createElement('form');
        form.method = 'POST';
        form.action = '${pageContext.request.contextPath}/makeUserChat';

        const input = document.createElement('input');
        input.type = 'hidden';
        input.name = 'email';
        input.value = email;

        form.appendChild(input);
        document.body.appendChild(form);
        form.submit();
    }
</script>

<script>
    // 테이블 상단 및 하단의 횡적 스크롤바 동기화 함수
    document.addEventListener("DOMContentLoaded", function () {
        const topScroll = document.getElementById("top-scroll");
        const bottomScroll = document.getElementById("bottom-scroll");

        topScroll.addEventListener("scroll", function () {
            bottomScroll.scrollLeft = topScroll.scrollLeft;
        });

        bottomScroll.addEventListener("scroll", function () {
            topScroll.scrollLeft = bottomScroll.scrollLeft;
        });
    });
</script>

<script>

    //닉네임 변경을 위한 팝업창 띄우기
    function openUpdateNicknamePopup(email, nickname) {
        window.open('${pageContext.request.contextPath}/admin/updateNickname?email=' + encodeURIComponent(email) + '&nickname=' + encodeURIComponent(nickname),
            'nicknameUpdatePopup',
            'width=600,height=400');
    }
</script>

<%@ include file="../include/footer.jsp" %>