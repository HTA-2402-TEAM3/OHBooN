<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>


    <div class="container">
        <footer class="py-3 my-4">
            <p class="text-center text-body-secondary">© 2024 JHTA Company</p>
        </footer>
    </div>

    <!-- Modal -->
<c:if test="${modal.isState eq 'show'}">
    <div class="modal fade" id="modal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="staticBackdropLabel">${modal.title}</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    ${modal.msg}
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Confirm</button>
                    <%--<button type="button" class="btn btn-primary">confirm</button>--%>
                </div>
            </div>
        </div>
    </div>

    <script>
        const modal = new bootstrap.Modal("#modal");
        modal.show();
    </script>
    <c:remove var="modal" scope="session"></c:remove>

</c:if>

</body>


</html>

