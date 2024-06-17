<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="container">
        <div>
            <input type="text" id="keyword" placeholder="검색할 장소를 입력하세요">
            <button onclick="searchPlaces()">검색</button>
        </div>
        <div id="placesList"></div>
        <div id="map" style="width:50%;height:300px;"></div>
        <div>
            <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=787689d01ab967ba84d8c08bc1dc540c&libraries=services"></script>
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
                    infowindow = new kakao.maps.InfoWindow({zindex:1}); // 클릭한 위치에 대한 주소를 표시할 인포윈도우입니다

                let address_test;
                var address;

                // 현재 지도 중심좌표로 주소를 검색해서 지도 좌측 상단에 표시합니다
                searchAddrFromCoords(map.getCenter(), displayCenterInfo);

                // 지도를 클릭했을 때 클릭 위치 좌표에 대한 주소정보를 표시하도록 이벤트를 등록합니다
                kakao.maps.event.addListener(map, 'click', function(mouseEvent) {
                    searchDetailAddrFromCoords(mouseEvent.latLng, function(result, status) {
                        address_test = result[0];
                        address = result[0].road_address.address_name;

                        if (status === kakao.maps.services.Status.OK) {
                            var detailAddr = !!result[0].road_address ? '<div><small>도로명주소 : ' + result[0].road_address.address_name + '</small></div>' : '';
                            detailAddr += '<div><small>지번 주소 : ' + result[0].address.address_name + '</div>';

                            var content = '<div class="bAddr">' +
                                '<span class="title">'+ address_test.road_address.building_name+ '</span>' +
                                detailAddr +
                                '</div>';

                            // 마커를 클릭한 위치에 표시합니다
                            marker.setPosition(mouseEvent.latLng);
                            marker.setMap(map);

                            // 인포윈도우에 클릭한 위치에 대한 법정동 상세 주소정보를 표시합니다
                            infowindow.setContent(content);
                            infowindow.open(map, marker);
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
                        var infoDiv = document.getElementById('centerAddr');

                        for(var i = 0; i < result.length; i++) {
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
            <button id="sendRequest">요청 보내기</button>

            <script>
                document.getElementById('sendRequest').addEventListener('click', function() {
                    console.log(address_test);
                    // XMLHttpRequest 객체 생성
                    var xhr = new XMLHttpRequest();

                    // 요청을 초기화: 'POST' 메서드와 URL을 설정
                    xhr.open('POST', 'http://localhost:8080/board/write', true);

                    // 요청 헤더 설정 (예: JSON 데이터를 보낼 경우)
                    xhr.setRequestHeader('Content-Type', 'application/json;charset=UTF-8');

                    // 요청이 성공적으로 완료되었을 때 실행될 함수
                    xhr.onload = function() {
                        if (xhr.status >= 200 && xhr.status < 300) {
                            // 응답을 처리
                            console.log('서버 응답:', xhr.responseText);
                        } else {
                            // 에러 처리
                            console.error('에러 발생:', xhr.statusText);
                        }
                    };

                    // 요청이 실패했을 때 실행될 함수
                    xhr.onerror = function() {
                        console.error('요청 실패');
                    };

                    // 서버로 보낼 데이터
                    var data = JSON.stringify({
                        subject: '게시물 제목',
                        content: '게시물 내용'
                    });

                    // 요청을 서버로 전송
                    xhr.send(data);
                });
            </script>
        </div>
        <h2 class="mt-5 mb-5">글쓰기</h2>

        <form action="../board/write" method="post">
            <div class="mb-3">
                <label for="subject" class="form-label">${address}</label>
                <input type="text" class="form-control" id="subject" placeholder="제목을 쓰세요" name="subject">
            </div>


            <div class="mb-3">
                <label for="content" class="form-label">내용</label>
                <textarea name="content" id="content" placeholder="내용을 입력하세요." rows="8" class="form-control"></textarea>
            </div>
            <div>
                <button class="btn btn-primary" id="btn-sign">CONFIRM</button>
                <button class="btn btn-secondary" type="reset">RESET</button>
            </div>
        </form>
    </div>
</body>
</html>