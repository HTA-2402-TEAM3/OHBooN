<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="../include/header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>JSP - Hello World</title>

</head>
<div class="row">
    <!-- 사이드바 -->
    <aside class="col-md-3 bg-light py-3">
        <div class="accordion" id="accordionExample">
            <div class="accordion-item">
                <h2 class="accordion-header">
                    <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                        카테고리
                    </button>
                </h2>
                <div id="collapseOne" class="accordion-collapse collapse show" data-bs-parent="#accordionExample">
                    <div class="accordion-body">
                        카테고리가 들어갈 예정입니다.
                    </div>
                </div>
            </div>
            <div class="accordion-item">
                <h2 class="accordion-header">
                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                        시간
                    </button>
                </h2>
                <div id="collapseTwo" class="accordion-collapse collapse" data-bs-parent="#accordionExample">
                    <div class="accordion-body">
                        시간이 들어갈 예정입니다.
                    </div>
                </div>
            </div>
            <div class="accordion-item">
                <h2 class="accordion-header">
                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                        지역
                    </button>
                </h2>
                <div id="collapseThree" class="accordion-collapse collapse" data-bs-parent="#accordionExample">
                    <div class="accordion-body">
                        지역이 들어갈 예정입니다.
                    </div>
                </div>
            </div>
            <button type="button" class="btn btn-light">패스트 매칭 하러 가기 >>></button>
        </div>

    </aside>

    <!-- 메인 콘텐츠 -->
    <div class="container">
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
                            <td>${totalCount - param.page * 10 - status.index}</td>
                            <td>

                                <div>
                                    <span>${board.nickName}</span>
                                    <span> ${board.evaluation}</span>
                                    <span> ${board.regDate}</span>
                                </div>

                                <div>
                                    <c:choose>
                                        <c:when test="${board.viewOption eq 1}">
                                            <a href="../board/view?boardID=${board.id}">
                                                <c:if test="${not empty board.category}">[${board.category}]</c:if> ${board.subject} ${board.regDate}
                                            </a>
                                        </c:when>

                                        <c:otherwise>
                                        <span>
                                            <c:if test="${not empty board.category}">[${board.category}]</c:if> ${board.subject} ${board.regDate}
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

                        <%--                    <td>--%>
                        <%--                        <f:parseDate var="regdate" value="${board.regDate}" pattern="yyyy-MM-dd"></f:parseDate>--%>
                        <%--                        <f:formatDate value="${regdate}" pattern="yyyy/MM/dd"></f:formatDate>--%>
                        <%--                    </td>--%>

                    </c:forEach>

                    </tbody>
                </table>


                <c:set var="link" value=""/>

                <c:choose>
                    <c:when test="${not empty meetDate}">
                        <c:set var="link" value="&meetDate=${meetDate}&location=${location}&category${category}"/>
                    </c:when>

                    <c:otherwise>
                        <c:set var="link" value="&searchOption=${searchOption}&searchWord=${searchWord}"/>
                    </c:otherwise>

                </c:choose>

                <div class="container">
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

                <div class="col-3">
                    <label>
                        <input type="text" name="searchWord" class="form-control" value="${searchWord}">
                    </label>
                </div>

                <div class="col-2">
                    <button type="submit" class="btn btn-primary">검색</button>
                </div>
            </div>

        </form>


        <div class="mt-5 mb-5"><a href="../board/write" class="btn btn-primary">WRITE</a></div>
    </div>


<%@ include file="../include/footer.jsp" %>