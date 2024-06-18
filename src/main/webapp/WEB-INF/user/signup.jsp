<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<%@ include file="../include/header.jsp" %>
<div class="container">
    <h2 class="mt-5 mb-5">회원가입</h2>

    <form action="../user/signup" method="post" enctype="multipart/form-data">

        <input type="hidden" id="agreeInfoOfferValue" name="agreeInfoOfferValue" value="false">

        <div class="mb-3">
            <div class="row g-3">
                <div class="col-auto">
                    <label for="email" class="form-label">USER E-MAIL</label>
                    <input type="email" class="form-control" id="email" placeholder="user email" name="email">
                    <button type="button" id="btn-duplicate-email" class="btn btn-dark mt-2">EMAIL CHECK</button>
                    <button type="button" id="btn-revise-email" class="btn btn-outline-primary mt-2">Modify</button>
                </div>
            </div>
        </div>

        <div class="mb-3">
            <div class="row g-3">
                <div class="col-auto">
                    <label for="nickname" class="form-label">USER NICKNAME</label>
                    <input type="text" class="form-control" id="nickname" placeholder="user nickname" name="nickname">
                    <button type="button" id="btn-duplicate-nickname" class="btn btn-dark mt-2">NICKNAME CHECK</button>
                    <button type="button" id="btn-revise-nickname" class="btn btn-outline-primary mt-2">Modify</button>
                </div>
            </div>
        </div>

        <div class="mb-3">
            <div class="row g-3">
                <div class="col-auto">
                    <label for="userPW" class="form-label">PASSWORD</label>
                    <input type="password" class="form-control" id="userPW" placeholder="password" name="userPW">
                    <div class="invalid-feedback invalid-feedback-length"></div>
                    <div class="invalid-feedback invalid-feedback-uppercase"></div>
                    <div class="invalid-feedback invalid-feedback-lowercase"></div>
                    <div class="invalid-feedback invalid-feedback-number"></div>
                    <div class="invalid-feedback invalid-feedback-special"></div>
                </div>
            </div>
        </div>
        <div class="mb-3">
            <div class="row g-3">
                <div class="col-auto">
                    <label for="userPW02" class="form-label">PASSWORD CONFIRM</label>
                    <input type="password" class="form-control" id="userPW02" placeholder="password confirm" >
                    <div class="invalid-feedback invalid-feedback-confirm"></div>
                </div>
            </div>
        </div>
        <div class="mb-3">
            <div class="row g-3">
                <div class="col-auto">
                    <label for="phone" class="form-label">CELLPHONE NUMBER</label>
                    <input type="text" class="form-control" id="phone" name="phone" placeholder="cellphone number" >
                    <div class="invalid-feedback invalid-feedback-phone-number"></div>
                    <div class="invalid-feedback invalid-feedback-phone-length"></div>
                </div>
            </div>
        </div>
        <div class="mb-3">
            <div class="row g-3">
                <div class="col-auto">
                    <label for="userName" class="form-label">USER NAME</label>
                    <input type="text" class="form-control" id="userName" placeholder="user name" name="userName">
                    <div class="invalid-feedback invalid-feedback-check_userName"></div>
                </div>
            </div>
        </div>
        <div class="mb-3">
            <div class="row g-3">
                <div class="col-auto">
                    <label for="birth" class="form-label">BIRTH DATE</label>
                    <input type="date" class="form-control" id="birth" placeholder="select birth date" name="birth">
                </div>
            </div>
        </div>
        <div class="mb-3">
            <label for="profile" class="form-label">PROFILE</label>
            <input class="form-control" type="file" id="profile"
                   name="profile" accept=".gif, .jpg, .png">
        </div>

        <div class="mb-3">
            <div id="preview">미리보기 이미지 들어가는 곳</div>
        </div>

        </br>

        <div> <a href="../user/terms" class="btn btn-info" target="_blank">약관 및 개인정보 보호정책</a> </div>
        <div>
            <input type="checkbox" id="agreeTerms"  />
            <label for="agreeTerms">위의 약관 및 개인정보 보호정책을 확인하였고 그 내용에 동의합니다.</label>
        </div>
        <div>
            <input type="checkbox" id="agreeInfoOffer" name="agreeInfoOffer" />
            <label for="agreeInfoOffer">(선택) 사용자 맞춤형 광고 제공을 위한 정보 수집 및 제3자 제공에 동의합니다. </label>
        </div>

        </br>

        <div>
            <button class="btn btn-primary" id="btn-sign">SIGN UP</button>
            <button class="btn btn-outline-primary" type="reset">RESET</button>
        </div>
    </form>
</div>

<script>
    // 폼 제출 처리
    $(document).ready(function() {
        $("#btn-sign").click(function(event) {
            event.preventDefault(); // 기본 제출 동작 방지

            // 선택약관 체크박스 체크 여부 갱신
            $("#agreeInfoOfferValue").val($("#agreeInfoOffer").prop('checked'));

            if (allWrittenCheck()) {
                $("form").submit(); // 모든 검사 항목 통과 시 폼 제출
            }
        });
    });

    //프로필 사진 넣기
    $("#profile").on("change", function(e){
        const file = e.currentTarget.files[0]; // 파일 선택

        if (!file) { // 파일 선택 안 함
            $("#preview").html(""); // 미리보기 영역 비우기
            return false;
        }

        const extension = file.name.substring(file.name.lastIndexOf(".")+1).toLowerCase(); // 확장자 확인
        // 뒤에서부터 '.' 을 찾아 그 다음에 적힌 string 을 뽑아옴 => 확장자명을 의미
        console.log(extension);
        if (!(extension === "png" || extension === "jpg" || extension === "gif")) { // 확장자가 png, jpg, gif가 아닐 경우
            alert("이미지 파일(png, jpg, gif)만 업로드 가능합니다.");
            $(this).val("");
            $("#preview").html(""); // 미리보기 영역 비우기
            return false;
        }else {
            const profileReader = new FileReader(); // FileReader 객체 생성
            profileReader.addEventListener("load", function(e){
                const img = e.target.result; // 미리보기 이미지 설정
                $("#preview").html(`<img src="\${img}" width="200" height="200">`);
            });
            profileReader.readAsDataURL(file); // 파일 데이터를 base64 형식으로 읽음
        }
    });

    //이메일 유효성 및 중복 검사
    function emailDuplicateCheck(){
        const email=$("#email").val();
        const check_email = /^[a-zA-Z0-9]([-_.]?[a-zA-Z0-9])*@[a-zA-Z0-9]([-_.]?[a-zA-Z0-9])*.[a-zA-Z]{2,3}$/i; // 이메일 정규식
        isEmailChecked=false;

        if(email === "") {
            alert("이메일을 입력해주세요.");
            $("#email").val("").focus();
            return false;
        }else if(email.indexOf(" ")>0){
            alert("이메일을 공백없이 작성해주세요");
            $("#email").val("").focus();
            return false;
        }else if(!check_email.test(email)){
            alert("이메일 형식에 맞게 작성해주세요!");
            $("#email").val("").focus();
            return false;
        } else{
            $.ajax({
                url:"/user/email-check",
                data: {
                    email:$("#email").val()
                },
                method:"post",
                success:function(data) {
                    console.log(data);
                    if(data.count>0) {
                        alert("이미 존재하는 이메일입니다. 다른 이메일을 사용해주세요.")
                        $("#email").attr("readonly", false).val("").focus();
                        return false;
                    }else {
                        const askUsingEmail = confirm("사용 가능한 이메일입니다. 사용하시겠습니까?");
                        if(askUsingEmail) {
                            savedEmail = email;
                            $("#email").attr("readonly", true); // 읽기만 가능
                            isEmailChecked = true; // 이메일 체크 true 로 반환
                            return isEmailChecked;
                        }else {
                            $("#email").attr("readonly", false).val("").focus();
                            return false;
                        }
                    }
                },
                fail:function(error) {
                    console.log(error);
                    return false;
                }
            });
            return false;
        }
    }

    // 이메일 최종검사
    function finalCheckEmail(){
        const check_email =  RegExp(/^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i);
        isEmailChecked=false;

        if(!check_email.test($("#email").val())){
            alert("오류 발생. 이메일 중복체크를 다시 해주세요.");
            $("#email").attr("readonly", false).val("").focus();
        } else{
            $.ajax({
                url:"/user/email-check",
                data: {
                    email:$("#email").val()
                },
                method:"post",
                success:function(data) {
                    console.log(data);
                    if(data.count>0) {
                        alert("이미 사용중인 이메일입니다. 다른 이메일을 입력해주세요.")
                        $("#email").attr("readonly", false).val("").focus();
                        return false;
                    }else {
                        isEmailChecked = true; // 이메일 체크 true 로 반환
                    }
                },
                fail:function(error) {
                    console.log(error);
                }
            });
        }
        return isEmailChecked;
    }

    //닉네임 중복 검사 함수
    function nicknameDuplicateCheck(){
        const nickname=$("#nickname").val();
        const check_nickname = RegExp(/^[a-z0-9]{4,10}$/); // 닉네임 유효성 검사용 변수 (영문 소문자, 숫자만 이용하여 4-10글자 가능)
        isNicknameChecked=false;

        if(nickname === "") {
            alert ("닉네임을 입력해주세요.");
            $("#nickname").attr("readonly", false).val("").focus();
        }else if(nickname.indexOf(" ")>0){
            alert("닉네임을 공백없이 작성해주세요");
            $("#nickname").attr("readonly", false).val("").focus();
        }else if (!check_nickname.test(nickname)) {
            alert("닉네임은 영문 소문자와 숫자만을 사용하여 4-10자리를 입력해주세요.");
            $("#nickname").attr("readonly", false).val("").focus();
        }else{
            $.ajax({
                url:"/user/nickname-check",
                data: {
                    nickname:$("#nickname").val()
                },
                method:"post",
                success:function(data) {
                    //console.log(data);
                    if(data.count>0) {
                        isNicknameChecked = false;
                        alert("이미 존재하는 닉네임입니다. 다른 닉네임을 사용해주세요.")
                        $("#nickname").attr("readonly", false).val("").focus();
                    }else {
                        const askUsingNickname = confirm("사용 가능한 닉네임입니다. 사용하시겠습니까?");
                        if(askUsingNickname) {
                            savedNickname = nickname; // 닉네임 저장
                            $("#nickname").attr("readonly", true); // 읽기만 가능
                            isNicknameChecked = true; // 닉네임 체크 true
                        }else {
                            isNicknameChecked = false;
                            $("#nickname").attr("readonly", false).val("").focus();
                        }
                    }
                },
                fail:function(error) {
                    console.log(error);
                    isNicknameChecked = false;
                    alert("알 수 없는 오류 발생. 다시 시도해주세요.");
                }
            });
            return false;
        }
    }

    // 닉네임 최종검사
    function finalCheckNickname(){
        const check_nickname = RegExp(/^[a-z0-9]{4,10}$/);
        isNicknameChecked=false;

        if(!check_nickname.test($("#nickname").val())){
            alert("오류 발생. 닉네임 중복체크를 다시 해주세요.");
            $("#nickname").attr("readonly", false).val("").focus();
        } else{
            $.ajax({
                url:"/user/nickname-check",
                data: {
                    nickname:$("#nickname").val()
                },
                method:"post",
                success:function(data) {
                    console.log(data);
                    if(data.count>0) {
                        alert("이미 사용중인 닉네임입니다. 다른 닉네임을 입력해주세요.")
                        $("#nickname").attr("readonly", false).val("").focus();
                        return false;
                    }else {
                        isNicknameChecked = true; // 이메일 체크 true 로 반환
                        return true;
                    }
                },
                fail:function(error) {
                    console.log(error);
                }
            });
        }
        return isNicknameChecked;
    }

    // 모든 항목 검사 함수
    function allWrittenCheck(){

        if($("#email").val().trim()==="") {
            alert("Email을 작성해주세요.");
            $("#email").attr("readonly", false).val("").focus();
            return false;
        }
        if(isEmailChecked===false){
            alert("이메일 중복체크를 해주세요.");
            $("#email").focus();
            return false;
        }
        finalCheckEmail(); // 이메일 유효성 최종검사

        if($("#nickname").val().trim()==="") {
            alert("닉네임을 작성해주세요.");
            $("#nickname").attr("readonly", false).val("").focus();
            return false;
        }
        if(isNicknameChecked===false){
            alert("닉네임 중복체크를 해주세요.");
            $("#nickname").focus();
            return false;
        }
        finalCheckNickname(); // 닉네임 유효성 최종검사

        if($("#userPW").val().trim()==="") {
            alert("패스워드를 입력해주세요.");
            $("#userPW").val("").focus();

            return false;
        }
        if(isValidPW===false){
            alert("올바른 비밀번호를 입력해주세요.")
            $("#userPW").focus();
            return false;
        }

        if($("#userPW02").val().trim()==="") {
            alert("패스워드 확인을 작성해주세요.");
            $("#userPW02").focus();
            return false;
        }
        if(!checkPWConfirm()) {
            alert("패스워드 확인이 비밀번호와 일치하지 않습니다. 다시 확인해주세요");
            $("#userPW02").focus();
            return false;
        }
        finalCheckPW(); // 비밀번호 유효성 최종검사

        if($("#phone").val().trim()==="") {
            alert("휴대폰 번호를 작성해주세요.");
            $("#phone").val("").focus();
            return false;
        }
        if(isValidPhoneNum === false) {
            alert("올바른 휴대폰 번호를 작성해주세요.");
            $("#phone").focus();
            return false;
        }

        if($("#userName").val().trim()==="") {
            alert("회원의 이름을 입력해주세요.");
            $("#userName").focus();
            return false;
        }


        if($("#birth").val()==="") {
            alert("생년월일을 입력해주세요.");
            $("#birth").focus();
            return false;
        }

        if(!$("#agreeTerms").prop('checked')){
            alert("약관 동의에 체크해주세요")
            $("#agreeTerms").focus();
            return false;
        }

        console.log("======================");
        return true;
    }

    //비밀번호 유효성 최종검사
    function finalCheckPW(){
        isValidPW = false;
        const userPW=$("#userPW").val();
        var check_pw = RegExp(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{9,20}$/);
        // 비밀번호 유효성 검사용 변수 (영문 대소문자 각 1개 이상, 숫자 1개 이상, 특수문자 1개 이상, 총 9~20자리)

        if (!check_pw.test(userPW)) {
            $("#userPW02").val("");
            $("#userPW").val("").focus();
            return false;
        }else{isValidPW = true;}
    }

    // 휴대폰 번호 유효성 검사 및 출력
    function checkPhone() {
        const phone = $("#phone").val();
        var checkPhoneLength  = /^.{9,14}$/; // 길이 제한: 9~14
        var checkPhoneNumber = /^[0-9]+$/; // 숫자만 입력 가능

        if (phone !== "") {
            isValidPhoneNum = true;
            $(".invalid-feedback.invalid-feedback-phone-number").hide();
            $(".invalid-feedback.invalid-feedback-phone-length").hide();

            if (!checkPhoneNumber.test(phone)) {
                $(".invalid-feedback.invalid-feedback-phone-number").text("휴대폰 번호는 숫자만 작성해주세요.").show();
                isValidPhoneNum = false;
            }
            if (!checkPhoneLength.test(phone)) {
                $(".invalid-feedback.invalid-feedback-phone-length").text("휴대폰 번호는 9자리 이상 14자리 이하로 작성해주세요.").show();
                isValidPhoneNum = false;
            }
        }
    }

    // 패스워드 유효성 검사
    function checkPW() {
        const userPW = $("#userPW").val();
        const conditions = [
            {
                regex: /^.{9,20}$/,
                selector: ".invalid-feedback.invalid-feedback-length",
                message: "비밀번호는 9자리 이상, 20자리 이하이어야 합니다."
            },
            {
                regex: /[A-Z]/,
                selector: ".invalid-feedback.invalid-feedback-uppercase",
                message: "영문 대문자를 포함해야 합니다."
            },
            {
                regex: /[a-z]/,
                selector: ".invalid-feedback.invalid-feedback-lowercase",
                message: "영문 소문자를 포함해야 합니다."
            },
            {
                regex: /[0-9]/,
                selector: ".invalid-feedback.invalid-feedback-number",
                message: "숫자를 포함해야 합니다."
            },
            {
                regex: /[!@#$%^&*]/,
                selector: ".invalid-feedback.invalid-feedback-special",
                message: "특수문자를 포함해야 합니다."
            }
        ];

        if (userPW !== "") {
            isValidPW = true;
            conditions.forEach(condition => {
                if (!condition.regex.test(userPW)) {
                    isValidPW = false;
                    $(condition.selector).text(condition.message).show();
                } else {
                    $(condition.selector).hide();
                }
            });

            if (isValidPW) {
                conditions.forEach(condition => {
                    $(condition.selector).hide();
                });
            }
        }
    }

    //PW와 PWConfirm 일치 여부 출력
    function checkPWConfirm(){
        if($("#userPW02").val() !== $("#userPW").val()){
            $(".invalid-feedback.invalid-feedback-confirm").text("password가 일치하지 않습니다.").show();
            return false;
        }else{
            $(".invalid-feedback.invalid-feedback-confirm").text("").hide();
            return true;
        }
    }

    // 사용자 이름 유효성 검사
    function checkUserName() {
        const userName = $("#userName").val();
        const check_userName = /^[가-힣a-zA-Z]+([가-힣a-zA-Z\s]*)$/;
        isValidUserName = false;

        if (userName !== "") {
            isValidUserName = true;

            if (!check_userName.test(userName)) {
                isValidUserName = false;
                $(".invalid-feedback.invalid-feedback-check_userName").text("신분증에 작성된 이름으로 2~20자로 작성해주세요. 한글, 영문 대∙소문자만 작성 가능합니다.").show();
            } else {
                $(".invalid-feedback.invalid-feedback-check_userName").text("").hide();
            }
        }
    }

</script>

<script>

    var isEmailChecked = false; // 이메일 중복체크 실시 여부
    var isNicknameChecked = false; // 닉네임 중복체크를 실시 여부
    var isValidPW = false;  // 패스워드가 유효한지 검토
    var isValidPhoneNum = false; // 전화번호가 유효한지 검토
    var isValidUserName = false; // 사용자 이름이 유효한지 검토


    // 회원가입 버튼 -> 모든 작성 항목 확인
    //$("#btn-sign").on("click", allWrittenCheck);

    // 이메일 중복 체크 버튼 -> 이메일 중복검사
    $("#btn-duplicate-email").on("click", emailDuplicateCheck);

    // 닉네임 중복 체크 버튼 -> 닉네임 중복검사
    $("#btn-duplicate-nickname").on("click", nicknameDuplicateCheck);

    // 이메일 수정 버튼 -> 저장된 이메일 삭제
    $("#btn-revise-email").on("click",function(){
        isEmailChecked = false;
        $("#email").val("").attr("readonly", false).focus();
    });

    // 닉네임 수정 버튼 -> 저장된 닉네임 삭제
    $("#btn-revise-nickname").on("click",function(){
        isNicknameChecked = false;
        $("#nickname").val("").attr("readonly", false).focus();
    });

    // 화면에 '패스워드'와 '패스워드 확인' 불일치 여부 표시
    $("#userPW02").on("keyup", checkPWConfirm);

    // 화면에 패스워드의 유효성 여부를 표시
    $("#userPW").on("keyup",checkPW);

    // 화면에 휴대폰 번호의 유효성 여부를 표시
    $("#phone").on("keyup", checkPhone);

    // 화면에 사용자 이름의 유효성 여부를 표시
    $("#userName").on("keyup", checkUserName);

</script>


<%@ include file="../include/footer.jsp" %>
