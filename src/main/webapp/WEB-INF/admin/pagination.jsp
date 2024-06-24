
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<nav aria-label="Page navigation example">
    <ul class="pagination">
        <c:if test="${currentPage > 1}">
            <li class="page-item">
                <a class="page-link" href="${pageContext.request.contextPath}/admin/userList?page=${currentPage - 1}&limit=${limit}" aria-label="Previous">
                    <span aria-hidden="true">&laquo;</span>
                </a>
            </li>
            <li class="page-item">
                <a class="page-link" href="${pageContext.request.contextPath}/admin/userList?page=1&limit=${limit}" aria-label="Previous">
                    <span aria-hidden="true">&lt;</span>
                </a>
            </li>
        </c:if>

        <c:forEach var="i" begin="1" end="${totalPages}">
            <c:choose>
                <c:when test="${i == currentPage}">
                    <li class="page-item active" aria-current="page">
                        <span class="page-link">${i}</span>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/admin/userList?page=${i}&limit=${limit}">${i}</a></li>
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <c:if test="${currentPage < totalPages}">
            <li class="page-item">
                <a class="page-link" href="${pageContext.request.contextPath}/admin/userList?page=${currentPage + 1}&limit=${limit}" aria-label="Next">
                    <span aria-hidden="true">&gt;</span>
                </a>
            </li>
            <li class="page-item">
                <a class="page-link" href="${pageContext.request.contextPath}/admin/userList?page=${totalPages}&limit=${limit}" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
        </c:if>

    </ul>
</nav>