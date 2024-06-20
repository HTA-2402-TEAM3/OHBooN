<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="../include/header.jsp" %>

<main class="form-signin w-100 m-auto">
    <form action="${pageContext.request.contextPath}/user/login/password-reset" method="post">
        <input type="hidden" name="token" value="${token}">
        <div class="mb-3">
            <div class="row g-3">
                <div class="col-auto">
                    <label for="newPW" class="form-label">새 비밀번호 입력</label>
                    <input type="password" class="form-control" id="newPW" placeholder="new password" name="newPW" required>

                    <label for="newPWConfirm" class="form-label">새 비밀번호 확인</label>
                    <input type="password" class="form-control" id="newPWConfirm" placeholder="new password confirm" required>
                    <div class="invalid-feedback invalid-feedback-PWconfirm"></div>

                    <button type="submit" id="btn-send-password" class="btn btn-outline-primary mt-2">비밀번호 변경하기</button>
                </div>
            </div>
        </div>
    </form>
</main>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    var isValidPW = false;

    // 화면에 '패스워드'와 '패스워드 확인' 불일치 여부 표시
    $("#newPWConfirm").on("keyup", checkPWConfirm);

    // 새 PW 발송 단계
    $(document).ready(function() {
        $("#btn-send-password").click(function(event) {
            event.preventDefault(); // 기본 제출 동작 방지
            if (checkPW() && checkPWConfirm()) {
                $("form").submit();
            }
        });
    });

    // newPW의 유효성 검사
    function checkPW() {
        const newPW = $("#newPW").val();
        const check_pw = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{9,20}$/;

        if (newPW === "") {
            alert("PW를 입력해주세요.");
            return false;
        } else if (!check_pw.test(newPW)) {
            alert("PW는 영문 대소문자 각 1개 이상, 숫자 1개 이상, 특수문자 1개 이상 들어가야 하며 총 9~20자리 가능합니다.");
            $("#newPWConfirm").val("");
            $("#newPW").val("").focus();
            return false;
        } else {
            isValidPW = true;
            return true;
        }
    }

    // newPW와 newPWConfirm 일치 여부 출력
    function checkPWConfirm() {
        if ($("#newPW").val() !== $("#newPWConfirm").val()) {
            $(".invalid-feedback.invalid-feedback-PWconfirm").text("password가 일치하지 않습니다.").show();
            return false;
        } else {
            $(".invalid-feedback.invalid-feedback-PWconfirm").text("").hide();
            return true;
        }
    }
</script>

<%@ include file="../include/footer.jsp" %>
