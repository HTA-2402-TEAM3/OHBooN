<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="../include/header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>JSP - Hello World</title>

</head>
<div class="container">
    <div class="row">
        <!-- 사이드바 -->
        <aside class="col-md-3 bg-light py-3">
            <form id="info" action="/board/list" method="get">
                <div class="accordion" id="accordionExample">
                    <div class="accordion-item">
                        <h2 class="accordion-header">
                            <button class="accordion-button" type="button" data-bs-toggle="collapse"
                                    data-bs-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                                카테고리
                            </button>
                        </h2>
                        <div id="collapseOne" class="accordion-collapse collapse show"
                             data-bs-parent="#accordionExample">
                            <div class="accordion-body">
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" name="category" id="flexRadioDefault1"
                                           value="커리어 상담" checked>
                                    <label class="form-check-label" for="flexRadioDefault1">
                                        커리어 상담
                                    </label>
                                </div>
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" name="category" id="flexRadioDefault2"
                                           value="학업 및 교육">
                                    <label class="form-check-label" for="flexRadioDefault2">
                                        학업 및 교육
                                    </label>
                                </div>
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" name="category" id="flexRadioDefault3"
                                           value="창업 및 비즈니스">
                                    <label class="form-check-label" for="flexRadioDefault3">
                                        창업 및 비즈니스
                                    </label>
                                </div>
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" name="category" id="flexRadioDefault4"
                                           value="기술 및 개발">
                                    <label class="form-check-label" for="flexRadioDefault4">
                                        기술 및 개발
                                    </label>
                                </div>
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" name="category" id="flexRadioDefault5"
                                           value="가벼운 대화">
                                    <label class="form-check-label" for="flexRadioDefault5">
                                        가벼운 대화
                                    </label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="accordion-item">
                        <h2 class="accordion-header">
                            <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                                    data-bs-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                                시간
                            </button>
                        </h2>
                        <div id="collapseTwo" class="accordion-collapse collapse" data-bs-parent="#accordionExample">
                            <div class="accordion-body">
                                <input type="datetime-local" id="meetDate" placeholder="날짜를 입력해 주세요" name="meetDate">
                            </div>
                        </div>
                    </div>
                    <div class="accordion-item">
                        <h2 class="accordion-header">
                            <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                                    data-bs-target="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                                지역
                            </button>
                        </h2>
                        <div id="collapseThree" class="accordion-collapse collapse" data-bs-parent="#accordionExample">
                            <div class="accordion-body">
                                <label for="firstSelect"></label>
                                <select id="firstSelect" class="form-select" name="mainRegion">
                                    <option selected>지역을 선택해주세요 (시/도)</option>
                                    <option value="서울특별시">서울특별시</option>
                                    <option value="인천광역시">인천광역시</option>
                                    <option value="부산광역시">부산광역시</option>
                                    <option value="대전광역시">대전광역시</option>
                                    <option value="대구광역시">대구광역시</option>
                                    <option value="울산광역시">울산광역시</option>
                                    <option value="광주광역시">광주광역시</option>
                                    <option value="제주특별자치도">제주특별자치도</option>
                                    <option value="세종특별자치시">세종특별자치시</option>
                                    <option value="경기도">경기도</option>
                                    <option value="강원도">강원도</option>
                                    <option value="충청북도">충청북도</option>
                                    <option value="충청남도">충청남도</option>
                                    <option value="경상북도">경상북도</option>
                                    <option value="경상남도">경상남도</option>
                                    <option value="전라북도">전라북도</option>
                                    <option value="전라남도">전라남도</option>
                                </select>
                                <label for="secondSelect"></label>
                                <select id="secondSelect" class="form-select">
                                    <option selected>지역을 선택해주세요</option>
                                    <script>
                                        document.addEventListener("DOMContentLoaded", function () {
                                            var firstSelect = document.getElementById("firstSelect");
                                            var secondSelect = document.getElementById("secondSelect");

                                            var options = {
                                                "서울특별시": ["강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구", "노원구", "도봉구", "동대문구", "동작구", "마포구", "서대문구", "서초구", "성동구", "성북구", "송파구", "양천구", "영등포구", "용산구", "은평구", "종로구", "중구", "중랑구"],
                                                "인천광역시": ["계양구", "남동구", "동구", "미추홀구", "부평구", "서구", "연수구", "중구"],
                                                "부산광역시": ["강서구", "금정구", "남구", "동구", "동래구", "부산진구", "북구", "사상구", "사하구", "서구", "수영구", "연제구", "영도구", "중구", "해운대구"],
                                                "대전광역시": ["대덕구", "동구", "서구", "유성구", "중구"],
                                                "대구광역시": ["남구", "달서구", "달성군", "동구", "북구", "서구", "수성구", "중구"],
                                                "울산광역시": ["남구", "동구", "북구", "중구", "울주군"],
                                                "광주광역시": ["광산구", "남구", "동구", "북구", "서구"],
                                                "제주특별자치도": ["서귀포시", "제주시"],
                                                "세종특별자치시": ["세종특별자치시"],
                                                "경기도": ["고양시", "과천시", "광명시", "광주시", "구리시", "군포시", "김포시", "남양주시", "동두천시", "부천시", "성남시", "수원시", "시흥시", "안산시", "안성시", "안양시", "양주시", "여주시", "오산시", "용인시", "의왕시", "의정부시", "이천시", "파주시", "평택시", "포천시", "하남시", "화성시", "가평군", "양평군", "연천군"],
                                                "강원도": ["강릉시", "동해시", "삼척시", "속초시", "원주시", "춘천시", "태백시", "고성군", "양구군", "양양군", "영월군", "인제군", "정선군", "철원군", "평창군", "홍천군", "화천군", "횡성군"],
                                                "충청북도": ["제천시", "청주시", "충주시", "괴산군", "단양군", "보은군", "영동군", "옥천군", "음성군", "증평군", "진천군"],
                                                "충청남도": ["계룡시", "공주시", "논산시", "당진시", "보령시", "서산시", "아산시", "천안시", "금산군", "부여군", "서천군", "예산군", "청양군", "태안군", "홍성군"],
                                                "경상북도": ["경산시", "경주시", "구미시", "김천시", "문경시", "상주시", "안동시", "영주시", "영천시", "포항시", "고령군", "군위군", "봉화군", "성주군", "영덕군", "영양군", "예천군", "울릉군", "울진군", "의성군", "청도군", "청송군", "칠곡군"],
                                                "경상남도": ["거제시", "김해시", "밀양시", "사천시", "양산시", "진주시", "창원시", "통영시", "거창군", "고성군", "남해군", "산청군", "의령군", "창녕군", "하동군", "함안군", "함양군", "합천군"],
                                                "전라북도": ["군산시", "김제시", "남원시", "익산시", "전주시", "정읍시", "고창군", "무주군", "부안군", "순창군", "완주군", "임실군", "장수군", "진안군"],
                                                "전라남도": ["광양시", "나주시", "목포시", "순천시", "여수시", "강진군", "고흥군", "곡성군", "구례군", "담양군", "무안군", "보성군", "신안군", "영광군", "영암군", "완도군", "장성군", "장흥군", "진도군", "함평군", "해남군", "화순군"]
                                            };


                                            firstSelect.addEventListener("change", function () {
                                                var selectedRegion = firstSelect.value;
                                                var districts = options[selectedRegion];

                                                secondSelect.innerHTML = "";

                                                if (districts) {
                                                    districts.forEach(function (district) {
                                                        var option = document.createElement("option");
                                                        option.value = district;
                                                        option.textContent = district;
                                                        secondSelect.appendChild(option);
                                                    });
                                                }
                                            });

                                            const firstSelectDiv = document.getElementById("fast-matching");
                                            const secondSelectDiv = document.getElementById("fast-matching");

                                            var form = document.getElementById("info");
                                            form.addEventListener("submit", function () {
                                                firstSelectDiv.innerHTML += '<input type="hidden" name="firstSelect" value="' + firstSelect.value + '">';
                                                secondSelectDiv.innerHTML += '<input type="hidden" name="secondSelect" value="' + secondSelect.value + '">';
                                                console.log(firstSelect)
                                                console.log(secondSelect)
                                            });

                                        });
                                    </script>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div id="fast-matching">

                        <button type="submit" class="btn btn-light">패스트 매칭 하러 가기 >>></button>
                    </div>
                </div>

            </form>
        </aside>

        <!-- 메인 콘텐츠 -->
        <main class="col-md-9 py-3">
            <h2 class="mt-5 mb-5">LIST</h2>

            <c:choose>
            <c:when test="${boards.size() le 0}">
            <p>게시글이 없습니다.</p>
            </c:when>

            <c:otherwise>


            <table class="table table-striped">
                <tbody>

                <c:forEach var="board" items="${requestScope.boards}" varStatus="status">
                    <tr>
                        <td>${status.count}</td>
                        <td>

                            <div>
                                <span>${board.nickName}</span>
                                <span> ${board.evaluation}</span>
                                <span> ${board.modifyDate}</span>
                            </div>

                            <div>
                                <c:choose>
                                    <c:when test="${board.viewOption eq 1}">
                                        <a href="../board/view?boardID=${board.id}">
                                            <c:if test="${not empty board.category}">[${board.category}]</c:if> ${board.subject} ${board.modifyDate}
                                        </a>
                                    </c:when>

                                    <c:otherwise>
                                        <span>
                                            <c:if test="${not empty board.category}">[매칭 완료]</c:if> ${board.subject} ${board.modifyDate}
                                        </span>
                                    </c:otherwise>
                                </c:choose>

                            </div>

                            <div>
                                <span>만남 희망 날짜 : ${board.meetDate}</span>
                                <span> 희망 지역 : ${board.location}</span>
                            </div>

                        </td>
                    </tr>

                </c:forEach>

                </tbody>
            </table>

                <c:set var="link" value=""/>

            <c:choose>
            <c:when test="${not empty meetDate}">
                <c:set var="link"
                       value="&meetDate=${meetDate}&firstSelect=${firstSelect}&secondSelect=${secondSelect}&category=${category}"/>
            </c:when>

            <c:otherwise>
                <c:set var="link" value="&searchOption=${searchOption}&searchWord=${searchWord}"/>
            </c:otherwise>

            </c:choose>

            <div class="container mt-4">
                <nav aria-label="Page navigation example">
                    <ul class="pagination d-flex justify-content-center">

                        <c:if test="${startPage > 0}">
                            <li class="page-item"><a class="page-link"
                                                     href="/board/list?page=${startPage - 1}${link}">Previous</a>
                            </li>
                        </c:if>

                        <c:forEach begin="${startPage}" end="${endPage}" var="i">
                            <c:choose>
                                <c:when test="${param.page == i}">
                                    <li class="page-item active"><a class="page-link"
                                                                    href="/board/list?page=${i}${link}">${i + 1}</a>
                                    </li>
                                </c:when>

                                <c:otherwise>

                                    <c:choose>
                                        <c:when test="${empty param.page && i == 0}">
                                            <li class="page-item active"><a class="page-link"
                                                                            href="/board/list?page=${i}${link}">${i + 1}</a>
                                            </li>
                                        </c:when>

                                        <c:otherwise>

                                            <li class="page-item"><a class="page-link"
                                                                     href="/board/list?page=${i}${link}">${i + 1}</a>
                                            </li>
                                        </c:otherwise>

                                    </c:choose>

                                </c:otherwise>
                            </c:choose>
                        </c:forEach>

                        <c:if test="${endPage != totalPage}">
                            <li class="page-item"><a class="page-link"
                                                     href="/board/list?page=${endPage + 1}${link}">Next</a>
                            </li>

                        </c:if>
                    </ul>
                </nav>
            </div>

            </c:otherwise>
            </c:choose>

            <form action="../board/list" method="get">

                <div class="row g-3 align-items-center">

                    <div class="col-2">
                        <select class="form-select" aria-label="Default select example" name="searchOption">
                            <option value="subject" ${searchOption eq "subject" ? "selected" : ""}>제목</option>
                            <option value="nickname" ${searchOption eq "nickname" ? "selected" : ""}>글쓴이</option>
                            <option value="content" ${searchOption eq "content" ? "selected" : ""}>내용</option>
                            <option value="all" ${searchOption eq "all" ? "selected" : ""}>제목 + 내용</option>
                        </select>
                    </div>

                    <div class="col-8">
                        <label>
                            <input type="text" name="searchWord" class="form-control" value="${searchWord}"
                                   style="width: 100%;">
                        </label>
                    </div>

                    <div class="col-2">
                        <button type="submit" class="btn btn-primary">검색</button>
                    </div>
                </div>

            </form>


            <div class="mt-5 mb-5"><a href="../board/write" class="btn btn-primary">WRITE</a></div>
    </div>
    </main>
</div>

<script>
    websocket = new WebSocket("ws://192.168.0.97:8080/chat");
</script>


<%@ include file="../include/footer.jsp" %>