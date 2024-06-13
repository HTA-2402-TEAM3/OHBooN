<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<%@ include file="../include/header.jsp" %>
<div class="container">
    <h2 class="mt-5 mb-5">회원가입</h2>

    <form action="../user/insert" method="post" enctype="multipart/form-data">

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
                </div>
            </div>
        </div>

<%--        <div class="mb-3">--%>
<%--            <label for="postCode" class="form-label">POSTAL CODE</label>--%>
<%--            <div class="row g-3">--%>
<%--                <div class="col-auto"><input type="text" class="form-control" id="postCode" placeholder="post code" name="postCode"></div>--%>
<%--                <div class="col-auto"><button type="button" id="btn-postcode" class="btn btn-dark">우편번호 찾기</button></div>--%>
<%--            </div>--%>
<%--        </div>--%>
<%--        <div class="mb-3">--%>
<%--            <div class="row g-3">--%>
<%--                <div class="col-auto">--%>
<%--                    <label for="address" class="form-label">ADDRESS</label>--%>
<%--                    <input type="text" class="form-control" id="address" placeholder="address" name="address" readonly>--%>
<%--                </div>--%>
<%--                &lt;%&ndash;readonly를 적용하여 읽기만 가능하게 설정&ndash;%&gt;--%>
<%--            </div>--%>
<%--        </div>--%>
<%--        <div class="mb-3">--%>
<%--            <div class="row g-3">--%>
<%--                <div class="col-auto">--%>
<%--                    <label for="detail-address" class="form-label">DETAIL ADDRESS</label>--%>
<%--                    <input type="text" class="form-control" id="detail-address" placeholder="detail address" name="addressDetail">--%>
<%--                </div>--%>
<%--            </div>--%>
<%--        </div>--%>

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
                   name="profile" accept="image/gif, imgage/jpg, image/png">
        </div>

        <div class="mb-3">
            <div id="preview">미리보기 이미지 들어가는 곳</div>
        </div>

        <div>
            <button class="btn btn-primary" id="btn-sign">SIGN IN</button>
            <button class="btn btn-outline-primary" type="reset">RESET</button>
        </div>
    </form>
</div>

<script>
    //프로필 사진 넣기
    $("#profile").on("change", function(e){
        // console.log(e);
        // console.log(e.currentTarget.files[0]);
        const file = e.currentTarget.files[0]; // 예) amusic.mp3

        if (!file) { // 파일이 선택되지 않은 경우
            $("#preview").html(""); // 미리보기 영역 비우기
            return false;
        }

        const extension = file.name.substring(file.name.lastIndexOf(".")+1).toLowerCase(); // 확장자명 뽑기
        // 뒤에서부터 '.' 을 찾아 그 다음에 적힌 string 을 뽑아옴 => 확장자명을 의미
        console.log(extension);
        if (!(extension === "png" || extension === "jpg" || extension === "gif")) {
            alert("이미지 파일(png, jpg, gif)만 업로드 가능합니다.");
            $(this).val("");
            $("#preview").html(""); // 미리보기 영역 비우기
            return false;
        }else{
            const profileReader = new FileReader();
            profileReader.addEventListener("load", function(e){
                console.log(e);
                const img = e.currentTarget.result;
                $("#preview").html(`<img src="\${img}">`);
                // 자바스크립트 안에서 사용하는 $와 EL 문법에서 사용하는 $ 가 충돌을 일으킴. 이를 방지하기 위해 백슬래시(\) 를 추가
            });
            profileReader.readAsDataURL(file);
        }
    });

    /*
    // 만약 새로 생성되는 item
    $(".a").on("click", function(e){
        console.log(e.currentTarget); // 이벤트를 받는 그 자신
        console.log(e.target); //
        $("body")
            .append("<div class='d' style='width:50px; height: 50px; border: 1px solid #000 '>d</div>");
    });

    // 그냥 이벤트를 걸면 작동하지 않음
    $(".d").on("click", function(e){
        console.log(e.currentTarget); // 이벤트를 받는 그 자신
        console.log(e.target); //
    });

    // 이벤트 위임
    $("body").on("click",".d",function(e){
        console.log("ddd");
    });
*/

    //이메일 중복 검사 함수
    function emailDuplicateCheck(){
        const email=$("#email").val();
        let check_email =  RegExp(/^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i);
        // 이메일 유효성 검사용 변수

        // 이메일 검증 요소 추가
        if(email == "") {
            isEmailChecked=false;
            alert("이메일을 입력해주세요.");
            $("#email").val("");
            $("#email").focus();
        }else if(email.indexOf(" ")>0){
            isEmailChecked=false;
            alert("이메일을 공백없이 작성해주세요");
            $("#email").val("");
            $("#email").focus();
        }else if(!check_email.test(email)){
            isEmailChecked=false;
            alert("이메일 형식에 맞게 작성해주세요!");
            $("#email").val("");
            $("#email").focus();
            return false;
        } else{
            $.ajax({
                url:"/user/email-uniqueness-check",
                data: {
                    email:$("#email").val()
                },
                method:"post",
                success:function(data) {
                    console.log(data);
                    if(data.count>0) {
                        isEmailChecked = false;
                        alert("이미 존재하는 이메일입니다. 다른 이메일을 사용해주세요.")
                        $("#email").focus();
                    }else {
                        const askUsingEmail = confirm("사용 가능한 이메일입니다. 사용하시겠습니까?");
                        if(askUsingEmail) {
                            $("#email").attr("readonly", true); // 읽기만 가능
                            isEmailChecked = true; // 이메일 체크 true 로 반환
                        }else {
                            isEmailChecked = false;
                            $("#email").val(""); // 공백으로 만듦
                            $("#email").focus();
                        }
                    }
                },
                fail:function(error) {
                    isEmailChecked = false;
                    console.log(error);
                }
            });
            return false;
        }
    }

    //닉네임 중복 검사 함수
    function nicknameDuplicateCheck(){
        const nickname=$("#nickname").val();
        var check_nickname = RegExp(/^[a-z0-9]{4,10}$/); // 닉네임 유효성 검사용 변수 (영문 소문자, 숫자만 이용하여 4-10글자 가능)

        if(nickname == "") {
            isNicknameChecked=false;
            alert ("닉네임을 입력해주세요.");
            $("#nickname").val("");
            $("#nickname").focus();
        }else if(nickname.indexOf(" ")>0){
            isNicknameChecked=false;
            alert("닉네임을 공백없이 작성해주세요");
            $("#nickname").val("");
            $("#nickname").focus();
        }else if (!check_nickname.test(nickname)) {
            isNicknameChecked=false;
            alert("닉네임은 영문 소문자와 숫자만을 사용하여 4-10자리를 입력해주세요.");
            $("#nickname").val("");
            $("#nickname").focus();
        }else{
            $.ajax({
                url:"/user/nickname-uniqueness-check",
                data: {
                    nickname:$("#nickname").val()
                },
                method:"post",
                success:function(data) {
                    console.log(data);
                    if(data.count>0) {
                        isNicknameChecked = false;
                        alert("이미 존재하는 닉네임입니다. 다른 닉네임을 사용해주세요.")
                        $("#nickname").val("");
                        $("#nickname").focus();
                    }else {
                        const askUsingNickname = confirm("사용 가능한 닉네임입니다. 사용하시겠습니까?");
                        if(askUsingNickname) {
                            $("#nickname").attr("readonly", true); // 읽기만 가능
                            isNicknameChecked = true; // 닉네임 체크 true 로 반환
                        }else {
                            isNicknameChecked = false;
                            $("#nickname").val(""); // 공백으로 만듦
                            $("#nickname").focus();
                        }
                    }
                },
                fail:function(error) {
                    isNicknameChecked = false;
                    console.log(error);
                }
            });
            return false;
        }
    }

    // 모든 항목 검사 함수
    function allWrittenCheck(){
        if($("#email").val().trim()==="") {
            alert("Email을 작성해주세요.");
            $("#email").focus();
            return false;
        }
        if(isEmailChecked===false){
            alert("이메일 중복체크를 해주세요.");
            $("#email").focus();
            return false;
        }
        if($("#nickname").val().trim()==="") {
            alert("닉네임을 작성해주세요.");
            $("#nickname").focus();
            return false;
        }
        if(isNicknameChecked===false){
            alert("닉네임 중복체크를 해주세요.");
            $("#nickname").focus();
            return false;
        }
        if($("#userPW").val().trim()==="") {
            alert("패스워드는 필수 입력사항입니다.");
            $("#userPW").focus();
            return false;
        }
        checkPW(); // 비밀번호 유효성 검사
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
        if($("#userPW02").val()!=$("#userPW").val()) {
            alert("패스워드 확인이 비밀번호와 일치하지 않습니다. 다시 확인해주세요");
            $("#userPW02").focus();
            return false;
        }
        if($("#phone").val().trim()==="") {
            alert("휴대폰 번호를 작성해주세요.");
            $("#phone").focus();
            return false;
        }
        if(isValidPhoneNum === false) {
            alert("올바른 휴대폰 번호를 작성해주세요.");
            $("#phone").focus();
            return false;
        }

        /* //우편번호, 주소, 세부주소 미사용
        if($("#postCode").val().trim()==="") {
            alert("우편번호를 작성해주세요.");
            $("#postCode").focus();
            return false;
        }
        if($("#address").val().trim()==="") {
            alert("주소를 작성해주세요.");
            $("#address").focus();
            return false;
        }
        if($("#detail-address").val().trim()==="") {
            alert("세부 주소를 작성해주세요.");
            $("#detail-address").focus();
            return false;
        }
         */
    }

    //비밀번호 유효성 검사 함수
    function checkPW(){
        isValidPW = false;
        const userPW=$("#userPW").val();
        var check_pw = RegExp(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{9,20}$/);
        // 비밀번호 유효성 검사용 변수 (영문 대소문자 각 1개 이상, 숫자 1개 이상, 특수문자 1개 이상, 총 9~20자리)

        if (!check_pw.test(userPW)) {
            alert("패스워드는 영문 대문자, 소문자, 숫자, 특수문자(!@#$%^&*)를 각 1개 이상 포함하여 9-20글자를 입력해주세요.");
            $("#userPW").val("");
            $("#userPW02").val("");
            $("#userPW").focus();
            isValidPW = false;
        }else{
            isValidPW = true;
        }
    }

    /* // 다음 주소찾기 매서드 -> 미사용
    function makePostcode() {
        new daum.Postcode({
            onComplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수

                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                if(data.userSelectedType === 'R'){
                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있고, 공동주택일 경우 추가한다.
                    if(data.buildingName !== '' && data.apartment === 'Y'){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                    if(extraAddr !== ''){
                        extraAddr = ' (' + extraAddr + ')';
                    }
                    // 조합된 참고항목을 해당 필드에 넣는다.
                    // document.getElementById("sample6_extraAddress").value = extraAddr;
                    $("#detail-address").val(extraAddr);

                } else {
                    // document.getElementById("sample6_extraAddress").value = '';
                    $("#detail-address").val("");
                }


                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                // document.getElementById('sample6_postcode').value = data.zonecode;
                // document.getElementById("sample6_address").value = addr;
                $("#postCode").val(data.zonecode);
                $("#address").val(addr);
                // 커서를 상세주소 필드로 이동한다.
                // document.getElementById("sample6_detailAddress").focus();
                $("#detail-address").focus();
            }
        }).open();
    }
     */
</script>

<script>
    var isEmailChecked = false; // 이메일 중복체크를 통과했는지 확인을 위한 변수
    var isNicknameChecked = false; // 닉네임 중복체크를 통과했는지 확인을 위한 변수
    var isValidPW = false;  // 패스워드가 유효한지를 표시
    var isValidPhoneNum = false; // 전화번호가 유효한지 표시

    // 회원가입 버튼 -> 모든 작성항목 확인
    $("#btn-sign").on("click", allWrittenCheck);

    // 이메일 중복 체크 버튼 -> 이메일 중복검사
    $("#btn-duplicate-email").on("click",emailDuplicateCheck);

    // 닉네임 중복 체크 버튼 -> 닉네임 중복검사
    $("#btn-duplicate-nickname").on("click",nicknameDuplicateCheck);

    // 우편번호 선택 -> 다음 주소찾기 코드 (미사용)
    //$("#btn-postcode").on("click",makePostcode);

    // 이메일 수정 버튼 -> 저장된 이메일 삭제
    $("#btn-revise-email").on("click",function(){
        isEmailChecked = false;
        $("#email").val("");
        $("#email").attr("readonly", false);
        $("#email").focus();
    });

    // 닉네임 수정 버튼 -> 저장된 닉네임 삭제
    $("#btn-revise-nickname").on("click",function(){
        isNicknameChecked = false;
        $("#nickname").val("");
        $("#nickname").attr("readonly", false);
        $("#nickname").focus();
    });

    // 화면에 '패스워드'와 '패스워드 확인' 불일치 여부 표시
    $("#userPW02").on("keyup",function(e){
        //console.log("키를 눌렀다 뗐습니다.")
        if($("#userPW02").val() !== $("#userPW").val()){
            $(".invalid-feedback.invalid-feedback-confirm").text("password가 일치하지 않습니다.").show();
            $(".invalid-feedback.invalid-feedback-confirm").show();
        }else{
            $(".invalid-feedback.invalid-feedback-confirm").hide();
            $(".invalid-feedback.invalid-feedback-confirm").text("");
        }
    });

    // 화면에 휴대폰 번호의 유효성 여부를 표시
    $("#phone").on("keyup",function(e){
        const phone = $("#phone").val();
        var checkPhoneLength  = /^.{9,14}$/; // 길이 제한: 9~14
        var checkPhoneNumber = /^[0-9]+$/; // 숫자만 입력 가능

        isValidPhoneNum = true;

        $(".invalid-feedback").hide();

        if (phone !== "") {
            if (!checkPhoneNumber.test(phone)) {
                $(".invalid-feedback.invalid-feedback-phone-number").text("휴대폰 번호는 숫자만 작성해주세요.").show();
                isValidPhoneNum = false;
            }
            if (!checkPhoneLength.test(phone)) {
                $(".invalid-feedback.invalid-feedback-phone-length").text("휴대폰 번호는 9자리 이상 14자리 이하로 작성해주세요.").show();
                isValidPhoneNum = false;
            }
            if (isValidPhoneNum) {
                $(".invalid-feedback").hide();
            }
        }
    });

    // 화면에 '패스워드' 유효성 여부 표시하는 함수
    $("#userPW").on("keyup", function(e) {
        const userPW = $("#userPW").val();
        var check_pw_length = /^.{9,20}$/;
        var check_pw_uppercase = /[A-Z]/;
        var check_pw_lowercase = /[a-z]/;
        var check_pw_number = /[0-9]/;
        var check_pw_special = /[!@#$%^&*]/;

        var isValidPW = true;

        $(".invalid-feedback").hide();

        if (userPW !== "") {
            if (!check_pw_length.test(userPW)) {
                $(".invalid-feedback.invalid-feedback-length").text("비밀번호는 9자리 이상, 20자리 이하이어야 합니다.").show();
                isValidPW = false;
            }
            if (!check_pw_uppercase.test(userPW)) {
                $(".invalid-feedback.invalid-feedback-uppercase").text("영문 대문자를 포함해야 합니다.").show();
                isValidPW = false;
            }
            if (!check_pw_lowercase.test(userPW)) {
                $(".invalid-feedback.invalid-feedback-lowercase").text("영문 소문자를 포함해야 합니다.").show();
                isValidPW = false;
            }
            if (!check_pw_number.test(userPW)) {
                $(".invalid-feedback.invalid-feedback-number").text("숫자를 포함해야 합니다.").show();
                isValidPW = false;
            }
            if (!check_pw_special.test(userPW)) {
                $(".invalid-feedback.invalid-feedback-special").text("특수문자를 포함해야 합니다.").show();
                isValidPW = false;
            }

            if (isValidPW) {
                $(".invalid-feedback").hide();
            }
        }
    });
</script>


<%@ include file="../include/footer.jsp" %>
