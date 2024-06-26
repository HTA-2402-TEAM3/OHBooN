
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<nav aria-label="Page navigation example">
    <ul class="pagination">
        <%--첫 페이지로 이동하는 바튼--%>
        <li class="page-item">
            <a class="page-link" href="${pageContext.request.contextPath}/admin/userList?page=1&limit=${limit}&searchKeyword=${searchKeyword}&searchField=${searchField}&sortField=${sortField}&sortOrder=${sortOrder}" aria-label="First">
                <span aria-hidden="true">First</span>
            </a>
        </li>

        <%--이전 페이지 그룹으로 이동하는 바튼--%>
        <li class="page-item">
            <c:if test="${currentPage <= pageGroupSize}">
                <a class="page-link" href="${pageContext.request.contextPath}/admin/userList?page=${currentPage - pageGroupSize}&limit=${limit}&searchKeyword=${searchKeyword}&searchField=${searchField}&sortField=${sortField}&sortOrder=${sortOrder}" aria-label="Previous">
                    <span aria-hidden="true">&laquo;</span>
                </a>
            </c:if>
            <c:if test="${currentPage > pageGroupSize}">
                <a class="page-link" href="${pageContext.request.contextPath}/admin/userList?page=${currentPage - pageGroupSize}&limit=${limit}" aria-label="Previous">
                    <span aria-hidden="true">&laquo;</span>
                </a>
            </c:if>
        </li>

        <%--이전 페이지로 이동하는 바튼--%>
        <li class="page-item">
            <c:if test="${currentPage == 1}">
                <a class="page-link" href="${pageContext.request.contextPath}/admin/userList?page=1&limit=${limit}&searchKeyword=${searchKeyword}&searchField=${searchField}&sortField=${sortField}&sortOrder=${sortOrder}" aria-label="Previous">
                    <span aria-hidden="true">&lt;</span>
                </a>
            </c:if>
            <c:if test="${currentPage > 1}">
                <a class="page-link" href="${pageContext.request.contextPath}/admin/userList?page=${currentPage - 1}&limit=${limit}&searchKeyword=${searchKeyword}&searchField=${searchField}&sortField=${sortField}&sortOrder=${sortOrder}" aria-label="Previous">
                    <span aria-hidden="true">&lt;</span>
                </a>
            </c:if>
        </li>

        <%--숫자 클릭해서 해당 페이지로 이동하는 바튼--%>
        <c:forEach var="i" begin="${groupStartPage}" end="${groupEndPage}">
            <c:choose>
                <c:when test="${i == currentPage}">
                    <li class="page-item active" aria-current="page">
                        <span class="page-link">${i}</span>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="page-item">
                        <a class="page-link" href="${pageContext.request.contextPath}/admin/userList?page=${i}&limit=${limit}
                            <c:if test='${searchKeyword != null and not empty searchKeyword}'>&searchKeyword=${searchKeyword}&searchField=${searchField}</c:if>
                            <c:if test='${sortField != null and not empty sortField}'>&sortField=${sortField}&sortOrder=${sortOrder}</c:if>">${i}
                        </a>
                    </li>
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <%--다음 페이지로 이동하는 바튼--%>
        <li class="page-item">
            <c:if test="${currentPage >= totalPages}">
                <a class="page-link" href="${pageContext.request.contextPath}/admin/userList?page=${totalPages}&limit=${limit}<c:if test='${searchKeyword != null and not empty searchKeyword}'>&searchKeyword=${searchKeyword}&searchField=${searchField}</c:if><c:if test='${sortField != null and not empty sortField}'>&sortField=${sortField}&sortOrder=${sortOrder}</c:if>" aria-label="Next">
                    <span aria-hidden="true">&gt;</span>
                </a>
            </c:if>
            <c:if test="${currentPage < totalPages}">
                <a class="page-link" href="${pageContext.request.contextPath}/admin/userList?page=${currentPage + 1}&limit=${limit}<c:if test='${searchKeyword != null and not empty searchKeyword}'>&searchKeyword=${searchKeyword}&searchField=${searchField}</c:if><c:if test='${sortField != null and not empty sortField}'>&sortField=${sortField}&sortOrder=${sortOrder}</c:if>" aria-label="Next">
                    <span aria-hidden="true">&gt;</span>
                </a>
            </c:if>
        </li>

        <%--다음 페이지 그룹으로 이동하는 바튼--%>
        <li class="page-item">
            <c:if test="${currentPage >= (totalPages/pageGroupSize-1)*pageGroupSize}">
                <a class="page-link" href="${pageContext.request.contextPath}/admin/userList?page=${totalPages}&limit=${limit}<c:if test='${searchKeyword != null and not empty searchKeyword}'>&searchKeyword=${searchKeyword}&searchField=${searchField}</c:if><c:if test='${sortField != null and not empty sortField}'>&sortField=${sortField}&sortOrder=${sortOrder}</c:if>" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </c:if>
            <c:if test="${currentPage < (totalPages/pageGroupSize-1)*pageGroupSize}">
                <a class="page-link" href="${pageContext.request.contextPath}/admin/userList?page=${currentPage + pageGroupSize}&limit=${limit}<c:if test='${searchKeyword != null and not empty searchKeyword}'>&searchKeyword=${searchKeyword}&searchField=${searchField}</c:if><c:if test='${sortField != null and not empty sortField}'>&sortField=${sortField}&sortOrder=${sortOrder}</c:if>" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </c:if>
        </li>

        <%--최종 페이지로 이동하는 바튼--%>
        <li class="page-item">
            <a class="page-link" href="${pageContext.request.contextPath}/admin/userList?page=${totalPages}&limit=${limit}<c:if test='${searchKeyword != null and not empty searchKeyword}'>&searchKeyword=${searchKeyword}&searchField=${searchField}</c:if><c:if test='${sortField != null and not empty sortField}'>&sortField=${sortField}&sortOrder=${sortOrder}</c:if>" aria-label="Next">
                <span aria-hidden="true">Last: ${totalPages}</span>
            </a>
        </li>
    </ul>
</nav>