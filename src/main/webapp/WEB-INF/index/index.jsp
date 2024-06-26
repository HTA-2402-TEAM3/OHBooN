<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="../include/header.jsp" %>
<div class="container">
    <h2 class="mt-5 mb-5"></h2>
    <div class="ohboon">
        <img src="/image/ohboon.png" alt="">
    </div>
    <div>
        <button type="submit" class="btn btn-light" onclick="location.href='/board/list'"> >>> </button>
    </div>
</div>
<script>
    var sessionNickname = '<%=session.getAttribute("sessionNickname")%>';
    console.log(sessionNickname.trim());

    document.addEventListener("DOMContentLoaded", function () {
        if (sessionNickname!=='null' && sessionNickname.trim()!==null) {
            const openPopup = window.open("/pop-up",
                "_blank", "width=450, height=200, top=100, left=100, popup=yes");
            openPopup.document.getElementById("evalEmpty");

            function openSocket() {
                alert("WebSocket is open!!!!");
                // Ensure only one connection is open at a time
                if (websocket !== undefined && websocket.readyState !== WebSocket.CLOSED) {
                    alert("WebSocket is already opened.");
                }
            }
            // Create a new instance of the websocket
            websocket = new WebSocket("ws://192.168.0.97:8080/chat");
            websocket.onopen = function (event) {
                if (event.data === undefined) return;
                openSocket();
            };
        } else {
            console.log("not logged in");
        }
    });
</script>
<%@ include file="../include/footer.jsp" %>
