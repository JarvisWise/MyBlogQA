<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!--- document.getElementById("commentText").value = ""; //or innerText --->
<html>
<head>
    <title>Post Page</title>
    <link rel="shortcut icon" href="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBzdGFuZGFsb25lPSJubyI/Pgo8IURPQ1RZUEUgc3ZnIFBVQkxJQyAiLS8vVzNDLy9EVEQgU1ZHIDIwMDEwOTA0Ly9FTiIKICJodHRwOi8vd3d3LnczLm9yZy9UUi8yMDAxL1JFQy1TVkctMjAwMTA5MDQvRFREL3N2ZzEwLmR0ZCI+CjxzdmcgdmVyc2lvbj0iMS4wIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciCiB3aWR0aD0iMjU2LjAwMDAwMHB0IiBoZWlnaHQ9IjI1Ni4wMDAwMDBwdCIgdmlld0JveD0iMCAwIDI1Ni4wMDAwMDAgMjU2LjAwMDAwMCIKIHByZXNlcnZlQXNwZWN0UmF0aW89InhNaWRZTWlkIG1lZXQiPgoKPGcgdHJhbnNmb3JtPSJ0cmFuc2xhdGUoMC4wMDAwMDAsMjU2LjAwMDAwMCkgc2NhbGUoMC4xMDAwMDAsLTAuMTAwMDAwKSIKZmlsbD0iIzAwMDAwMCIgc3Ryb2tlPSJub25lIj4KPHBhdGggZD0iTTkzMCAyNTQyIGMtMzUgLTE5IC01MDQgLTMxNyAtNTkwIC0zNzQgLTU4IC0zOSAtODcgLTc2IC0xMTYgLTE0NgpsLTI0IC01NyAwIC02NzMgYzAgLTczNSAtMiAtNzEyIDU4IC03NzUgMzggLTQwIDEzMiAtODYgMjExIC0xMDMgMTQ1IC0zMCAzNDUKLTEwIDM4NyA0MCAyMiAyNSAyMiAyOCAyNiA1ODMgNiA3NzAgLTIyIDcwMCAzOTggOTgzIDI0OCAxNjcgMjc2IDE4OSAyODcgMjIyCjI0IDc0IC0xNiAxMzggLTg3IDEzOCAtNDAgMCAtODkgLTI4IC0zODUgLTIyNyAtNDA3IC0yNzIgLTUzNiAtMzQzIC02MTkgLTM0MwotNTAgMCAtNzkgMjggLTc5IDc2IDAgNjYgMjQgOTcgMTIzIDE1OSA1MCAzMCAxNTMgOTUgMjI5IDE0NCBsMTM4IDg4IDMxIC0yOQpjNDQgLTM5IDkzIC0zOCAxMzMgMSAyOSAyOSAyOSAzMSAyOSAxNDAgMCAxMDAgLTIgMTEyIC0yMiAxMzYgLTMxIDM1IC04MyA0MgotMTI4IDE3eiIvPgo8cGF0aCBkPSJNMTczMCAyMjAyIGMtNDcgLTIyIC01OTIgLTQwMiAtNjM4IC00NDYgLTYyIC01OCAtODkgLTExMSAtMTAyIC0xOTIKLTcgLTQ3IC0xMCAtMjc5IC04IC03MDAgMyAtNjIzIDMgLTYyOSAyNSAtNjY5IDkxIC0xNzAgMzc0IC0yNDQgNTg0IC0xNTQgNzcKMzQgNzE4IDUxMSA3NTQgNTYyIDEzIDE5IDE1IDExMCAxNSA3MDAgbDAgNjc4IC0yNSAyNCBjLTE4IDE5IC0zNSAyNSAtNjkgMjUKLTQwIDAgLTU0IC04IC0xNDMgLTc1IC02MTIgLTQ2OSAtNjgxIC01MTMgLTgxMyAtNTIzIC02NyAtNCAtNzAgLTQgLTEwMSAyNwotMjYgMjcgLTMxIDM3IC0yNiA2NiAzIDE4IDEzIDQ1IDIyIDU4IDkgMTQgMTA5IDkwIDIyMyAxNzAgMjc1IDE5MiAyNDYgMTc2CjI3NyAxNDcgMzkgLTM3IDEwOCAtMzUgMTQxIDMgMjIgMjYgMjQgMzYgMjQgMTM4IDAgMTA5IDAgMTExIC0yOSAxNDAgLTI5IDI5Ci03NCAzNyAtMTExIDIxeiBtNDQwIC04MDQgbC0xIC05MyAtMjQwIC0xODUgYy0xMzIgLTEwMiAtMjQ0IC0xODYgLTI0OSAtMTg4Ci02IC0yIC0xMCAzMyAtMTAgODcgbDAgOTAgMjQzIDE5MCBjMTMzIDEwNCAyNDUgMTkwIDI1MCAxOTAgNCAxIDcgLTQxIDcgLTkxeiIvPgo8L2c+Cjwvc3ZnPgo=" />
</head>

<%String username=(String)session.getAttribute("current_username");%>

<body>
<jsp:include page="blog-header.jsp" />
<div class="container-fluid text-center">
    <div class="row content my-c">
        <div class="col-xs-8 col-xs-offset-2 text-left my">
            <!-- Post Itself -->
            <div style="text-align: center">
                <h3>Post</h3>
            </div>
            <div class="well">
                <table class="table table-bordered tb-my cp" >
                    <tbody>
                        <tr>
                            <th class="alert alert-info">${blogPost.getPostName()}</th>
                            <td class="alert alert-info">(Posted by: ${blogPostUser.getFullName()})</td>
                            <td class="alert alert-info">(Posted: ${blogPost.getPostDateTime()})</td>
                        </tr>
                        <tr>
                            <td class="bg-success" colspan="3">${blogPost.getPostText()}</td>
                        </tr>
                    </tbody>
                </table>
            </div>


            <!-- Comment List -->
            <div style="text-align: center">
                <h3>Comments</h3>
            </div>
            <c:forEach var="c" items="${commentSets}">
                <div class="well">
                    <table class="table table-bordered tb-my cp" >
                        <tbody>
                        <tr>
                            <th class="alert alert-info">${c.getCommentUser().getFullName()}</th>
                            <td class="alert alert-info">(Posted: ${c.getComment().getCommentDateTime()})</td>
                            <c:if test="${not empty c.getComment().getCommentParentId()}">
                                <td class="alert alert-info">[Replied to: ${c.getParentUser().getFullName()}]</td>
                            </c:if>
                            <td class="alert alert-info">
                                <input type="hidden" name="commentId" class="commentId" value="${c.getComment().getCommentId()}">
                                <input type="hidden" name="commentUserName" class="commentUserName" value="${c.getCommentUser().getFullName()}">
                                <button type="button" class="btn btn-default btn-reply">Reply</button>
                            </td>
                        </tr>
                            <c:choose>
                                <c:when test="${not empty c.getComment().getCommentParentId()}">
                                    <td class="bg-success" colspan="4">${c.getComment().getCommentText()}</td>
                                </c:when>
                                <c:otherwise>
                                    <td class="bg-success" colspan="3">${c.getComment().getCommentText()}</td>
                                </c:otherwise>
                            </c:choose>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </c:forEach>

            <!-- Form for new comment-->
            <div class="well">
                <form class="my" action="<c:url value='/add/comment?postId=${blogPost.getPostId()}' />" method="POST">
                    <div class="form-group">
                        <!--<div style="display: inline">
                            <span></span><span style="display: none" id="replyTo"></span>
                        </div>-->
                        <label for="commentText"><span><%=username%></span></label><span style="display: none" id="replyTo"></span>
                        <textarea name="commentText"
                            id="commentText"
                            class="form-control"
                            cols="46"
                            rows="10"
                            maxlength="3900"
                            placeholder="Enter Comment Text..." required></textarea>
                        <input type="hidden" name="parentId" id="parentId" value="">
                    </div>
                    <button type="submit" class="btn btn-default">Create Comment</button>
                    <button type="button" class="btn btn-default" id="btn-cancel">Cancel</button>
                </form>
            </div>
        </div>
    </div>
</div>
<jsp:include page="blog-footer.jsp" />
</body>
</html>

<!-- try move this block to the end of page?) -->
<script>
    document.querySelectorAll(".btn-reply").forEach(reply => {
        reply.addEventListener("click", function handleClick(event) {
            document.getElementById("parentId").value = reply.parentNode.querySelector(".commentId").value;
            document.getElementById("replyTo").style.display="inline";
            document.getElementById("replyTo").innerText = " [replays to " + reply.parentNode.querySelector(".commentUserName").value + "]";
        });
    });

    document.querySelectorAll("#btn-cancel").forEach(btnCancel => {
        btnCancel.addEventListener("click", function handleClick(event) {
            document.getElementById("parentId").value = "";
            document.getElementById("replyTo").style.display="none";
            document.getElementById("replyTo").innerText = "";
            document.getElementById("commentText").value = "";
        });
    });

    /*const btnCancel = document.getElementById("btn-cancel");
    btnCancel.addEventListener("click", function handleClick(event) {
        document.getElementById("parentId").value = "";
        document.getElementById("replyTo").style.display="none";
        document.getElementById("replyTo").innerText = "";
        document.getElementById("commentText").value = "";
    });*/
</script>
