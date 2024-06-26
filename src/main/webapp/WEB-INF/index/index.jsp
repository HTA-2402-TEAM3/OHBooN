<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="../include/header.jsp" %>
<div class="container">
    <h2 class="mt-5 mb-5"> INDEX PAGE </h2>
    <input type="hidden" name="resultEval" id="resultEval" value="">
    <div>
        <button type="submit" class="btn btn-light" onclick="local.href='/board/list'">패스트 매칭 하러 가기 >>></button>
    </div>
    <p>Session Email :  <%=session.getAttribute("sessionEmail")%>
    </p>
    <p>Session Nickname :  <%=session.getAttribute("sessionNickname")%>
    </p>
    <p>Session Profile :  <%=session.getAttribute("sessionProfile")%>
    </p>
    <p>Session Grade :  <%=session.getAttribute("sessionGrade")%>
    </p>
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
