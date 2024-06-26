<%--
  Created by IntelliJ IDEA.
  User: Dayou
  Date: 2024-06-25
  Time: 오후 2:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <link rel="stylesheet" href="../../css/popup.css">
    <title>팝업</title>
    <script src="https://kit.fontawesome.com/a2e8ca0ae3.js" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://code.jquery.com/jquery-latest.min.js"></script>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons"
          rel="stylesheet">

</head>
<body>

<input type="hidden" name="evalEmpty" id="evalEmpty" value="${requestScope.evalEmpty}">
<c:choose>
    <c:when test="${not empty requestScope.evaluationMap}">
        <form action="/match/evaluation" id="evaluationForm">
            <ul>
                <c:forEach var="MatchDTO" items="${evaluationMap}" varStatus="status">
                    <c:if test="${sessionEmail eq MatchDTO.email}">
                        <c:set var="userEmail" value="${MatchDTO.sender}"/>
                    </c:if>
                    <c:if test="${sessionEmail ne MatchDTO.email}">
                        <c:set var="userEmail" value="${MatchDTO.email}"/>
                    </c:if>
                    <li>
<%--                    <span> ${userEmail} </span>--%>
                    <%--  <button type="button" id="like-${status.index}">좋아요</button>
                              <button type="button" id="hate-${status.index}">싫어요</button>--%>
<%--                    <div id='vote' class='page'>--%>
                        <h1 id='product-names'>${userEmail}</h1>
                        <div id='circle-container'>
                            <div id='circle-one' class='active picked'>
                                <div class='overlay'></div>
                                <h1 class='votecount'></h1>
                                <button type="button" id="like-${status.index}" class="material-icons thumbup">thumb_up
                                </button>
                            </div>
                            <div id='circle-two' class='active picked'>
                                <div class='overlay'>
                                    <h1 class='votecount'></h1>
                                    <button type="button" id="hate-${status.index}" class="material-icons thumbdown">
                                        thumb_down
                                    </button>
                                </div>
                            </div>
<%--                        </div>--%>
                    </div>

                    <input type="hidden" id="match-id${status.index}" name="matchID"
                           value="${MatchDTO.matchID}">
                    <input type="hidden" id="email${status.index}" name="email" value="${userEmail}">
                    <input type="hidden" id="evaluation-result${status.index}" name="evaluationResult">
                    </li>
                </c:forEach>
            </ul>
            <div>
                <button type="button" id="btn-submit" class="">평가 완료</button>
            </div>
        </form>
        <script>
            document.addEventListener('DOMContentLoaded', function () {
                const evaluationsSize = `${evaluationMap.size()}`;
                console.log("size", evaluationsSize);

                for (let i = 0; i < evaluationsSize; i++) {

                    let evaluation = document.getElementById("evaluation-result" + i);

                    document.getElementById("like-" + i).addEventListener("click", function () {
                        console.log("like");
                        evaluation.value = 1;
                    })

                    document.getElementById("hate-" + i).addEventListener("click", function () {
                        console.log("hate");
                        evaluation.value = -1;
                    })
                }

                document.getElementById("btn-submit").addEventListener("click", function () {
                    console.log("btn-submit");
                    const evaluations = [];
                    for (let i = 0; i < evaluationsSize; i++) {
                        const matchID = document.getElementById("match-id" + i).value;
                        const email = document.getElementById("email" + i).value;
                        const evaluationResult = document.getElementById("evaluation-result" + i).value;
                        if (evaluationResult !== undefined && evaluationResult !== "") {
                            let evaluationObj = {
                                matchID: matchID,
                                email: email,
                                evaluationResult: evaluationResult
                            };
                            evaluations.push(evaluationObj);
                        }
                    }
                    const jsonData = JSON.stringify(evaluations);
                    console.log(jsonData);

                    $.ajax({
                        type: 'post',
                        url: '/evaluation',
                        data: jsonData,
                        dataType: 'json',
                        contentType: 'application/json; charset=UTF-8',
                        success: function (data) {
                            console.log(data)
                        },
                        error: function (err) {
                            console.log(err)
                        }
                    })
                });
            });
        </script>
    </c:when>
    <c:otherwise>
        <div>매칭된 대상이 없습니다.</div>
    </c:otherwise>
</c:choose>
</body>
<%--<script src="../js/evaluation-popup.js"></script>--%>
</html>
