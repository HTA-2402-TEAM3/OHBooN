var websocket;
var inputMessage = document.getElementById('messageInput');
var sessionNickname = '<%=session.getAttribute("sessionNickname")%>';
var enterChatObj = null;
var makeChatObj = null;

document.addEventListener("DOMContentLoaded", function () {
    if (sessionNickname && sessionNickname.trim() !== "") {
        // function openSocket() {
        writeResponse("WebSocket is open!!!!");
        // Ensure only one connection is open at a time
        if (websocket !== undefined && websocket.readyState !== WebSocket.CLOSED) {
            writeResponse("WebSocket is already opened.");
            return;
        }
        // Create a new instance of the websocket
        websocket = new WebSocket("ws://localhost:8080/chat");
        websocket.onopen = function (event) {
            if (event.data === undefined) return;
            writeResponse(event.data);
        };
        websocket.onmessage = function (event) {
            // ws객체에 전달받은 메세지가 있으면 실행되는 함수
            var message = event.data.split(":");
            // 보낼 때 id랑 같이 보냄. |로 구분
            var sender = message[0];
            // 보낸 사람 id
            var content = message[1];
            // msg
            console.log(message);
            var messages = document.getElementById("messages");
            messages.innerHTML += "<p class='chat_content'>" + sender + " : " + content + "</p>";

            // $("#messages").html($("#messages").html()
            //     + "<p class='chat_content'>" + sender + " : " + content + "</p>");
        };
        websocket.onclose = function (event) {
            writeResponse("Connection closed");
        };
        websocket.onerror = function (event) {
            console.log(event);
        }
    } else {
        console.log("user is not login...");
    }
});

// var inputMessage = document.getElementById('messageInput');

function sendMessage() {
    var text = document.getElementById("messageInput").value;
    //ws 객체에 session id랑 msg보냄

    var message = {
        match_id: enterChatObj.match_id,
        chat_id: enterChatObj.chat_id,
        user_id: enterChatObj.user_id,
        content: text,
        timestamp: new Date().toISOString()
    };

    websocket.send(JSON.stringify(message));
    inputMessage.value = "";
}

function closeSocket() {
    websocket.close();

}

function writeResponse(text) {
    var messages = document.getElementById("messages");
    messages.innerHTML += "<br/>" + text;
}

function matchBtn(user) {
    if (enterChatObj && enterChatObj.match_email) {
        var match_email = enterChatObj.match_email;
        document.getElementById("matchBtn").hidden = user !== match_email;
    } else {
        console.error("obj 또는 obj.match_email가 정의되지 않았습니다.");
    }
}
function matching() {
    $.ajax({
        method: 'post',
        url: '../match',
        data: {
            match_id: enterChatObj.match_id
        },
        success: function (data) {
            alert(JSON.stringify(data));
            if (data.isMatch > 0) {
                alert("매칭 확정 되었습니다.");
            } else {
                alert("err");
            }
        },
        error: function (e) {
            console.log(e);
        }
    })
}

async function enterChat(long) {
    console.log(long);
    try {
        const resp = await fetch("/enterChat?chat_id=" + long);
        console.log(resp);

        const data = await resp.json();
        console.log(data);

        enterChatObj = data;
    } catch (error) {
        console.error("errrrr", error);
    }
    showMsgList();
}
function showMsgList() {
    const messages = enterChatObj.msgMap;

    Object.values(messages).forEach((message) => {
        console.log({
            sender: message.sender,
            content : message.content
        });
    });
}
async function makeChat(text) {
    console.log(text);
    try {
        const resp = await fetch("/enterChat?chat_id=" + long);
        console.log(resp);

        const data = await resp.json();
        console.log(data);

        enterChatObj = data;
    } catch (error) {
        console.error("errrrr", err);
    }
}