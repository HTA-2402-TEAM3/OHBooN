<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
    <script>
        function validateForm(event) {
            let meetDate = document.getElementById('meetDate').value;
            let subject = document.getElementById('subject').value;
            let content = document.getElementById('content').value;
            let isValid = true;
            let message = "";


            if (!meetDate) {
                isValid = false;
                message += "희망 날짜 및 시간을 입력하세요.\n";
            }
            if (!subject) {
                isValid = false;
                message += "제목을 입력하세요.\n";
            }
            if (!content) {
                isValid = false;
                message += "내용을 입력하세요.\n";
            }

            if (!isValid) {
                alert(message);
                event.preventDefault();
            }
        }

        document.addEventListener('DOMContentLoaded', function () {
            document.getElementById("btn-sign").addEventListener('click', function () {

                console.log("btn click!!!!!");

                let meetDate = document.getElementById('meetDate').value;
                let subject = document.getElementById('subject').value;
                let content = document.getElementById('content').value;
                let isValid = true;
                let message = "";


                if (!meetDate) {
                    isValid = false;
                    message += "희망 날짜 및 시간을 입력하세요.\n";
                }
                if (!subject) {
                    isValid = false;
                    message += "제목을 입력하세요.\n";
                }
                if (!content) {
                    isValid = false;
                    message += "내용을 입력하세요.\n";
                }

                if (!isValid) {
                    alert(message);
                    return;
                }

                let boardForm = document.getElementById("boardForm");
                let boardData = new FormData(boardForm);

                boardData.forEach((value, key) => {
                    console.log(key, value);
                });

                fetch('/board/write', {
                    method: 'POST',
                    body: boardData
                })
                    .then(resp => {
                        if (!resp.ok) {
                            return resp.json().then(error => {
                                throw new Error(error.message);
                            });
                        }
                        return resp.json();
                    })
                    .then(data => {
                        console.log(data.message);
                        alert(data.writeMessage)
                        location.href = "/board/list"
                    })
                    .catch(error => {
                        console.log(error);
                        alert(error.message);
                    })
            });
        });
    </script>
</head>
<body>
<div class="container">
    <div class="container">
        <h2 class="mt-5 mb-5">글쓰기</h2>
        <div>
            <input type="text" id="keyword" placeholder="검색할 장소를 입력하세요">
            <button onclick="searchPlaces()">검색</button>
        </div>

        <form id="boardForm" action="../board/write" method="post">

            <c:if test="${not empty param.boardID}">
                <input type="hidden" name="boardID" value="${param.boardID}">
            </c:if>

            <div id="place"></div>
            <div id="map" style="width:50%;height:300px;"></div>
            <div class="mb-3">
                <label for="meetDate" class="form-label">희망 날짜 및 시간</label>
                <input type="datetime-local" id="meetDate" placeholder="날짜를 입력해 주세요" name="meetDate">
            </div>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="category" id="flexRadioDefault1" value="커리어 상담"
                       checked>
                <label class="form-check-label" for="flexRadioDefault1">
                    커리어 상담
                </label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="category" id="flexRadioDefault2" value="학업 및 교육">
                <label class="form-check-label" for="flexRadioDefault2">
                    학업 및 교육
                </label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="category" id="flexRadioDefault3" value="창업 및 비즈니스">
                <label class="form-check-label" for="flexRadioDefault3">
                    창업 및 비즈니스
                </label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="category" id="flexRadioDefault4" value="기술 및 개발">
                <label class="form-check-label" for="flexRadioDefault4">
                    기술 및 개발
                </label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="category" id="flexRadioDefault5" value="가벼운 대화">
                <label class="form-check-label" for="flexRadioDefault5">
                    가벼운 대화
                </label>
            </div>

            <div class="mb-3">
                <label for="subject" class="form-label">제목</label>
                <input type="text" class="form-control" id="subject" placeholder="제목을 쓰세요" name="subject">
            </div>
            <div class="mb-3">
                <label for="content" class="form-label">내용</label>
                <textarea name="content" id="content" placeholder="내용을 입력하세요." rows="8" class="form-control"></textarea>
            </div>
            <div>
                <input type="hidden" class="form-control" id="location" name="location" value="">
            </div>
            <div id="confirm">
                <button type="button" class="btn btn-primary" id="btn-sign">CONFIRM</button>
                <button class="btn btn-secondary" type="reset">RESET</button>
            </div>
        </form>
    </div>
</div>

<script type="text/javascript"
        src="//dapi.kakao.com/v2/maps/sdk.js?appkey=787689d01ab967ba84d8c08bc1dc540c&libraries=services"></script>
<script>
    var mapContainer = document.getElementById('map'), // 지도를 표시할 div
        mapOption = {
            center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
            level: 3 // 지도의 확대 레벨
        };
    // 지도를 생성합니다
    var map = new kakao.maps.Map(mapContainer, mapOption);
    // 마커가 표시될 위치입니다

    var geocoder = new kakao.maps.services.Geocoder();

    var marker = new kakao.maps.Marker(), // 클릭한 위치를 표시할 마커입니다
        infowindow = new kakao.maps.InfoWindow({zindex: 1}); // 클릭한 위치에 대한 주소를 표시할 인포윈도우입니다

    let address_test;
    var address;

    // 현재 지도 중심좌표로 주소를 검색해서 지도 좌측 상단에 표시합니다
    searchAddrFromCoords(map.getCenter(), displayCenterInfo);

    // 지도를 클릭했을 때 클릭 위치 좌표에 대한 주소정보를 표시하도록 이벤트를 등록합니다
    kakao.maps.event.addListener(map, 'click', function (mouseEvent) {
        searchDetailAddrFromCoords(mouseEvent.latLng, function (result, status) {
            address_test = result[0];
            address = result[0].road_address.address_name;

            if (status === kakao.maps.services.Status.OK) {
                var detailAddr = !!result[0].road_address ? '<div><small>도로명주소 : ' + result[0].road_address.address_name + '</small></div>' : '';
                detailAddr += '<div><small>지번 주소 : ' + result[0].address.address_name + '</div>';

                var content = '<div class="bAddr">' +
                    '<span class="title">' + address_test.road_address.building_name + '</span>' +
                    detailAddr +
                    '</div>';

                // 마커를 클릭한 위치에 표시합니다
                marker.setPosition(mouseEvent.latLng);
                marker.setMap(map);

                // 인포윈도우에 클릭한 위치에 대한 법정동 상세 주소정보를 표시합니다
                infowindow.setContent(content);
                infowindow.open(map, marker);

                var contentDiv = document.getElementById('location');
                contentDiv.value = address;
            }
        });
    });

    function searchAddrFromCoords(coords, callback) {
        // 좌표로 행정동 주소 정보를 요청합니다
        geocoder.coord2RegionCode(coords.getLng(), coords.getLat(), callback);
    }

    function searchDetailAddrFromCoords(coords, callback) {
        // 좌표로 법정동 상세 주소 정보를 요청합니다
        geocoder.coord2Address(coords.getLng(), coords.getLat(), callback);
    }

    // 지도 좌측상단에 지도 중심좌표에 대한 주소정보를 표출하는 함수입니다
    function displayCenterInfo(result, status) {
        if (status === kakao.maps.services.Status.OK) {


            for (var i = 0; i < result.length; i++) {
                // 행정동의 region_type 값은 'H' 이므로
                if (result[i].region_type === 'H') {
                    // infoDiv.innerHTML = result[i].address_name;
                    break;
                }
            }
        }
    }

    // 장소 검색 객체 생성
    var ps = new kakao.maps.services.Places();

    // 장소 검색 함수
    function searchPlaces() {
        var keyword = document.getElementById('keyword').value;

        if (!keyword.replace(/^\s+|\s+$/g, '')) {
            alert('키워드를 입력해주세요!');
            return false;
        }

        // 장소검색 객체를 통해 키워드로 장소검색을 요청합니다
        ps.keywordSearch(keyword, placesSearchCB);
    }

    // 장소검색이 완료됐을 때 호출되는 콜백함수
    function placesSearchCB(data, status, pagination) {
        if (status === kakao.maps.services.Status.OK) {

            // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기 위해 LatLngBounds 객체에 좌표를 추가합니다
            var bounds = new kakao.maps.LatLngBounds();


            bounds.extend(new kakao.maps.LatLng(data[0].y, data[0].x));


            // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
            map.setBounds(bounds);
        }
    }
</script>

</body>
</html>
