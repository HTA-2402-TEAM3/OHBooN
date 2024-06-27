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
                            <img src="${request.contextPath}/upload/${infoUserDto.profile}" class="profile" id="current-profile-img" >
                        </c:when>
                        <c:otherwise>
                            이미지없음
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <label for="profile" class="form-label">PROFILE 파일</label>
                    <input class="form-control" type="file" id="profile" name="profile" accept=".gif, .jpg, .png">
                    <button type="button" class="btn btn-dark mt-2" id="clear-profile-button">프로필 비우기</button>
                </td>
                <td>
                    <div class="mb-3">
                        <div id="preview">
                            <c:choose>
                                <c:when test="${not empty infoUserDto.profile}">
                                    <img src="${pageContext.request.contextPath}/upload/${infoUserDto.profile}" alt="Profile Image" class="img-thumbnail" id="preview-img"/>
                                </c:when>
                                <c:otherwise>
                                    이미지없음
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </td>
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
    let originalProfileHtml = document.getElementById('preview').innerHTML;
    let clearProfileClicked = false;

    let isValidPhone = false; // 전역 변수로 선언
    let profile = $("#profile").val();

    document.querySelector("form").addEventListener("submit", function(event) {
        console.log("form");
        phoneCheck();
        if (!profile) {
            var ask = confirm("프로필 사진이 없는 상태로 사용자 정보를 저장하시겠습니까?");
            if(ask){
                if (!isValidPhone) { // 전역 변수 isValidPhone을 직접 확인
                    alert("휴대폰 번호를 작성해주세요.(없이 숫자만 사용, 9~14자리)")
                    event.preventDefault();
                }
                return true;
            }
            else event.preventDefault();
        }
    });

    // 전화번호 유효성 검사
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

    // 프로필 사진 추가/변경시 적용
    document.getElementById('profile').addEventListener('change', function(e) {
        const file = e.target.files[0];
        if (!file) {
            document.getElementById('preview').innerHTML = originalProfileHtml;
            clearProfileClicked = false;
            return;
        }

        const extension = file.name.substring(file.name.lastIndexOf(".")+1).toLowerCase();
        if (!(extension === "png" || extension === "jpg" || extension === "gif")) {
            alert("이미지 파일(png, jpg, gif)만 업로드 가능합니다.");
            e.target.value = "";
            document.getElementById('preview').innerHTML = originalProfileHtml;
            clearProfileClicked = false;
            return;
        } else {
            const reader = new FileReader();
            reader.onload = function(e) {
                const img = e.target.result; // 미리보기 이미지
                document.getElementById('preview').innerHTML = `<img src="\${img}" width="200" height="200" class="img-thumbnail"/>`;
                clearProfileClicked = false;
            };
            reader.readAsDataURL(file);
        }
    });


    // 기존 프로필 사진 지우기
    document.getElementById('clear-profile-button').addEventListener('click', function() {
        const profileInput = document.getElementById('profile');
        profileInput.value = ""; // 파일 선택 취소
        const clearProfileInput = document.getElementById('clear-profile-input');
        if (!clearProfileInput) {
            const input = document.createElement('input');
            input.type = 'hidden';
            input.name = 'clearProfile';
            input.id = 'clear-profile-input';
            input.value = 'true';
            document.querySelector('form').appendChild(input);
        }
        document.getElementById('preview').innerHTML = '이미지없음';
        clearProfileClicked = true;
    });

</script>


<%@ include file="../include/footer.jsp" %>

