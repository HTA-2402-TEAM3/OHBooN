
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<div class="pagination">
    <c:if test="${currentPage > 1}">
        <a href="${pageContext.request.contextPath}/admin/userList?page=1&limit=${limit}">&laquo; </a>
        <a href="${pageContext.request.contextPath}/admin/userList?page=${currentPage - 1}&limit=${limit}">&lt; </a>
    </c:if>
    <c:forEach var="i" begin="1" end="${totalPages}">
        <c:choose>
            <c:when test="${i == currentPage}">
                <span>${i}</span>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/admin/userList?page=${i}&limit=${limit}">${i}</a>
            </c:otherwise>
        </c:choose>
    </c:forEach>
    <c:if test="${currentPage < totalPages}">
        <a href="${pageContext.request.contextPath}/admin/userList?page=${currentPage + 1}&limit=${limit}"> &gt;</a>
        <a href="${pageContext.request.contextPath}/admin/userList?page=${totalPages}&limit=${limit}"> &raquo;</a>
    </c:if>
</div>