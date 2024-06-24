<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="../include/header.jsp" %>

<div class="container">
    <h2 class="mt-5 mb-5"> 사용자 정보</h2>
    <form action="${pageContext.request.contextPath}/user/info/update" method="post" enctype="multipart/form-data">
        <table class="table table-bordered">
            <tr>
                <th>항목</th>
                <th>기존의 내용</th>
                <th>변경하고자 하는 내용</th>
                <th>변경 후</th>
            </tr>
            <tr>
                <th>PROFILE IMAGE</th>
                <td>
                    <c:choose>
                        <c:when test="${not empty infoUserDto.profile}">
                            <img src="${request.contextPath}/upload/${infoUserDto.profile}" class="profile">
                        </c:when>
                        <c:otherwise>
                            이미지없음
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <label for="profile" class="form-label">PROFILE 파일</label>
                    <input class="form-control" type="file" id="profile" name="profile" accept=".gif, .jpg, .png">
                </td>
                <c:choose>
                    <c:when test="${not empty infoUserDto.profile}">
                        <td>
                            <div class="mb-3">
                                <div id="preview">
                                    <img src="${pageContext.request.contextPath}/upload/${infoUserDto.profile}" alt="Profile Image" class="img-thumbnail" />
                                </div>
                            </div>
                        </td>
                    </c:when>
                    <c:otherwise>
                        <td>이미지없음</td>
                    </c:otherwise>
                </c:choose>

            </tr>
            <tr>
                <th>MEMBER GRADE</th>
                <td>${infoUserDto.grade}</td>
                <td>-</td>
                <td></td>
            </tr>
            <tr>
                <th>User EMAIL</th>
                <td>${infoUserDto.email}</td>
                <td>이메일 변경은 고객센터에 문의하세요</td>
                <td></td>
            </tr>
            <tr>
                <th>USER NICKNAME</th>
                <td>${infoUserDto.nickname}</td>
                <td>닉네임 변경은 고객센터에 문의하세요</td>
                <td></td>
            </tr>
            <tr>
                <th>USER NAME</th>
                <td>${infoUserDto.userName}</td>
                <td>개명사항은 고객센터에 문의하세요</td>
                <td></td>
            </tr>
            <tr>
                <th>USER ACCOUNT CREATED DATE</th>
                <td>${infoUserDto.createDate}</td>
                <td>-</td>
                <td></td>
            </tr>
            <tr>
                <th>USER PHONE NUMBER</th>
                <td>${infoUserDto.phone}</td>
                <td><input type="text" class="form-control" id="phone" placeholder="user phone number" name="phone" value="${infoUserDto.phone}" required></td>
                <td></td>
            </tr>
            <tr>
                <th>USER BIRTH DATE</th>
                <td>${infoUserDto.birth}</td>
                <td>생년월일 정정은 고객센터에 문의하세요</td>
                <td></td>
            </tr>
            <tr>
                <th>EVALUATION</th>
                <td>${infoUserDto.evaluation}</td>
                <td>-</td>
                <td></td>
            </tr>
            <tr>
                <th>AGREEMENT OF OFFERING INFORMATION</th>
                <td>${infoUserDto.agreeInfoOffer?"동의":"미동의"}</td>
                <td>
                    <div>
                        <label>
                            <input type="checkbox" class="form-check" name="agreeInfoOffer" ${infoUserDto.agreeInfoOffer ? "checked" : ""}>
                            <span>동의시 체크</span>
                        </label>
                    </div>
                </td>
                <td></td>

            </tr>
        </table>

        <div>
            <a href="${pageContext.request.contextPath}/user/info/password-update" class="btn btn-dark">비밀번호 변경</a>
            <button type="submit" class="btn btn-primary">변경사항 저장</button>
        </div>
    </form>
</div>


<script>
    let isValidPhone = false; // 전역 변수로 선언
    let profile = $(#profile).val();

    document.querySelector("form").addEventListener("submit", function(event) {
        console.log("form");
        //sphoneCheck();
        const phoneInput = $("#phone");
        var checkPhoneLength = /^.{9,14}$/; // 길이 제한: 9~14
        var checkPhoneNumber = /^[0-9]+$/; // 숫자만 입력 가능
        console.log(phoneInput.val());
        phoneCheck();
        if (profile.isEmpty) {
            var ask = confirm("프로필 사진이 없는 상태로 사용자 정보를 저장하시겠습니까?");
            if(ask) return true;
            else event.preventDefault();
        }
        if (!isValidPhone) { // 전역 변수 isValidPhone을 직접 확인
            alert("휴대폰 번호를 작성해주세요.(없이 숫자만 사용, 9~14자리)")
            event.preventDefault();
        }
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


    function phoneCheck() {
        //isValidPhone = false;
        const phoneInput = $("#phone");
        var checkPhoneLength = /^.{9,14}$/; // 길이 제한: 9~14
        var checkPhoneNumber = /^[0-9]+$/; // 숫자만 입력 가능
        console.log(phoneInput);
        if (phoneInput.val() === "" || !checkPhoneNumber.test(phoneInput.val()) || !checkPhoneLength.test(phoneInput.val())) { // phoneInput.val() 직접 사용
            if (phoneInput.val() === "") { // phoneInput.val() 직접 사용
                //alert("전화번호를 입력해주세요.");
            } else if (!checkPhoneNumber.test(phoneInput.val())) { // phoneInput.val() 직접 사용
                //alert("휴대폰 번호는 숫자만 작성해주세요.");
            } else if (!checkPhoneLength.test(phoneInput.val())) { // phoneInput.val() 직접 사용
                //alert("휴대폰 번호는 9자리 이상 14자리 이하로 작성해주세요.");
            }
            phoneInput.focus();
        } else {
            isValidPhone = true; // 전역 변수 isValidPhone을 true로 설정
        }
    }
</script>


<%@ include file="../include/footer.jsp" %>

