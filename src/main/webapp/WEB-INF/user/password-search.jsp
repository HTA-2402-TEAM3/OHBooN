<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<%@ include file="../include/header.jsp" %>

<main class="form-signin w-100 m-auto">
    <form action="${pageContext.request.contextPath}/user/login/password-search" method="post">
        <div class="mb-3">
            <div class="row g-3">
                <div class="col-auto">
                    <label for="email" class="form-label">사용자 E-MAIL 입력 </label>
                    <input type="email" class="form-control" id="email" placeholder="user email" name="email" required>
                    <button type="button" id="btn-search-email" class="btn btn-dark mt-2">이메일 검색</button>
                    <button type="button" id="btn-modify-email" class="btn btn-secondary mt-2">이메일 수정</button>

                    <button type="submit" id="btn-send-email" class="btn btn-outline-primary mt-2">비밀번호 변경 메일 보내기</button>
                </div>
            </div>
        </div>
    </form>
</main>

<script>

    // 이메일 검색 여부 체크하는 변수
    var isEmailChecked = false;

    //이메일 검색
    $("#btn-search-email").on("click", searchEmail);

    //이메일 수정
    $("#btn-modify-email").on("click", function(){
        isEmailChecked = false;
        $("#email").val("").attr("readonly", false).focus();
    });

    // PW 변경을 위한 메일 발송 단계
    $(document).ready(function() {
        $("#btn-send-email").click(function(event) {
            event.preventDefault(); // 기본 제출 동작 방지

            if(!isEmailChecked) {
                alert("이메일 검색을 해주세요.")
                return false;
            }else {
                const ask = confirm($("#email").val()+" 계정의 PW를 변경하시겠습니까?");
                if(ask) {
                    $("form").submit();
                    alert($("#email").val()+"로 PW 변경 링크를 발송하였습니다.")
                }else {
                    $("#email").attr("readonly", false).val("").focus();
                    return false;
                }
            }
        });
    });

    // 이메일 유효성 검사 후 DB에서 이메일 검색
    function searchEmail(){
        const check_email =  RegExp(/^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i);
        isEmailChecked=false;

        if($("#email").val()===""){
            alert("이메일을 입력해주세요.");
            $("#email").attr("readonly", false).val("").focus();
        }
        else if(!check_email.test($("#email").val())){
            alert("이메일 양식에 맞지 않습니다. 이메일 확인 후에 이메일 검색 버튼을 다시 눌러주세요.");
            $("#email").attr("readonly", false).val("").focus();
        } else{
            $.ajax({
                url: "${pageContext.request.contextPath}/user/email-check",
                data: {
                    email:$("#email").val()
                },
                method:"post",
                success:function(data) {
                    console.log(data);
                    if(data.count === 0) {
                        alert("이메일이 존재하지 않습니다. 이메일을 다시 확인해주세요.")
                        $("#email").attr("readonly", false).val("").focus();
                        return false;
                    }else {
                        const askUsingEmail = confirm( $("#email").val()+" 계정이 맞습니까?");
                        if(askUsingEmail) {
                            $("#email").attr("readonly", true); // 읽기만 가능
                            isEmailChecked = true; // 이메일 체크 true 로 반환
                            return isEmailChecked;
                        }else {
                            $("#email").attr("readonly", false).val("").focus();
                        }
                    }
                },
                fail:function(error) {
                    console.log(error);
                }
            });
        }
        return false;
    }

</script>

<%@ include file="../include/footer.jsp" %>
