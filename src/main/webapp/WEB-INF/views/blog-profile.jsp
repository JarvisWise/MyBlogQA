<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Profile</title>
    <link rel="shortcut icon" href="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBzdGFuZGFsb25lPSJubyI/Pgo8IURPQ1RZUEUgc3ZnIFBVQkxJQyAiLS8vVzNDLy9EVEQgU1ZHIDIwMDEwOTA0Ly9FTiIKICJodHRwOi8vd3d3LnczLm9yZy9UUi8yMDAxL1JFQy1TVkctMjAwMTA5MDQvRFREL3N2ZzEwLmR0ZCI+CjxzdmcgdmVyc2lvbj0iMS4wIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciCiB3aWR0aD0iMjU2LjAwMDAwMHB0IiBoZWlnaHQ9IjI1Ni4wMDAwMDBwdCIgdmlld0JveD0iMCAwIDI1Ni4wMDAwMDAgMjU2LjAwMDAwMCIKIHByZXNlcnZlQXNwZWN0UmF0aW89InhNaWRZTWlkIG1lZXQiPgoKPGcgdHJhbnNmb3JtPSJ0cmFuc2xhdGUoMC4wMDAwMDAsMjU2LjAwMDAwMCkgc2NhbGUoMC4xMDAwMDAsLTAuMTAwMDAwKSIKZmlsbD0iIzAwMDAwMCIgc3Ryb2tlPSJub25lIj4KPHBhdGggZD0iTTkzMCAyNTQyIGMtMzUgLTE5IC01MDQgLTMxNyAtNTkwIC0zNzQgLTU4IC0zOSAtODcgLTc2IC0xMTYgLTE0NgpsLTI0IC01NyAwIC02NzMgYzAgLTczNSAtMiAtNzEyIDU4IC03NzUgMzggLTQwIDEzMiAtODYgMjExIC0xMDMgMTQ1IC0zMCAzNDUKLTEwIDM4NyA0MCAyMiAyNSAyMiAyOCAyNiA1ODMgNiA3NzAgLTIyIDcwMCAzOTggOTgzIDI0OCAxNjcgMjc2IDE4OSAyODcgMjIyCjI0IDc0IC0xNiAxMzggLTg3IDEzOCAtNDAgMCAtODkgLTI4IC0zODUgLTIyNyAtNDA3IC0yNzIgLTUzNiAtMzQzIC02MTkgLTM0MwotNTAgMCAtNzkgMjggLTc5IDc2IDAgNjYgMjQgOTcgMTIzIDE1OSA1MCAzMCAxNTMgOTUgMjI5IDE0NCBsMTM4IDg4IDMxIC0yOQpjNDQgLTM5IDkzIC0zOCAxMzMgMSAyOSAyOSAyOSAzMSAyOSAxNDAgMCAxMDAgLTIgMTEyIC0yMiAxMzYgLTMxIDM1IC04MyA0MgotMTI4IDE3eiIvPgo8cGF0aCBkPSJNMTczMCAyMjAyIGMtNDcgLTIyIC01OTIgLTQwMiAtNjM4IC00NDYgLTYyIC01OCAtODkgLTExMSAtMTAyIC0xOTIKLTcgLTQ3IC0xMCAtMjc5IC04IC03MDAgMyAtNjIzIDMgLTYyOSAyNSAtNjY5IDkxIC0xNzAgMzc0IC0yNDQgNTg0IC0xNTQgNzcKMzQgNzE4IDUxMSA3NTQgNTYyIDEzIDE5IDE1IDExMCAxNSA3MDAgbDAgNjc4IC0yNSAyNCBjLTE4IDE5IC0zNSAyNSAtNjkgMjUKLTQwIDAgLTU0IC04IC0xNDMgLTc1IC02MTIgLTQ2OSAtNjgxIC01MTMgLTgxMyAtNTIzIC02NyAtNCAtNzAgLTQgLTEwMSAyNwotMjYgMjcgLTMxIDM3IC0yNiA2NiAzIDE4IDEzIDQ1IDIyIDU4IDkgMTQgMTA5IDkwIDIyMyAxNzAgMjc1IDE5MiAyNDYgMTc2CjI3NyAxNDcgMzkgLTM3IDEwOCAtMzUgMTQxIDMgMjIgMjYgMjQgMzYgMjQgMTM4IDAgMTA5IDAgMTExIC0yOSAxNDAgLTI5IDI5Ci03NCAzNyAtMTExIDIxeiBtNDQwIC04MDQgbC0xIC05MyAtMjQwIC0xODUgYy0xMzIgLTEwMiAtMjQ0IC0xODYgLTI0OSAtMTg4Ci02IC0yIC0xMCAzMyAtMTAgODcgbDAgOTAgMjQzIDE5MCBjMTMzIDEwNCAyNDUgMTkwIDI1MCAxOTAgNCAxIDcgLTQxIDcgLTkxeiIvPgo8L2c+Cjwvc3ZnPgo=" />
</head>
<body>
<%String userRole=(String)session.getAttribute("current_user_role");%>
<c:set var = "userRole" value = "<%=userRole%>"/>


<jsp:include page="blog-header.jsp" />
<div class="container-fluid text-center">
    <div class="row content my-c" > <!-- style="background: green;"-->
        <div class="col-xs-8 col-xs-offset-2 text-left my"> <!-- style="background: #fff; min-height: calc(100vh - 112px);" -->

            <!-- account data-->
            <div style="text-align: center">
                <h3>Profile</h3>
            </div>
            <div class="well">
                <form name="userForm" action="<c:url value='/edit/userCommon/${blogUser.getUserId()}' />" method="POST">
                    <div class="form-group text-left">
                        <label for="firstName">FirstName: </label>
                        <input type="text" value="${blogUser.getFirstName()}" class="form-control" name="firstName" required placeholder="Enter firstName..." id="firstName"/>
                    </div>
                    <div class="form-group text-left">
                        <label for="lastName">LastName: </label>
                        <input type="text" value="${blogUser.getLastName()}" class="form-control" name="lastName" required placeholder="Enter lastName..." id="lastName"/>
                    </div>
                    <div class="form-group text-left">
                        <label for="regUserName">Email: </label>
                        <input type="text" value="${blogUser.getEmail()}" class="form-control" name="regUserName" required disabled placeholder="Enter email" id="regUserName"/>
                    </div>
                    <div class="form-group text-left">
                        <label for="birthday">Birthday: </label>
                        <input type="text" class="form-control"  name="birthday" value="${blogUser.getBirthday()}" required placeholder="yyyy/mm/dd" id="birthday" pattern="(?:19|20)[0-9]{2}[/.](?:(?:0[1-9]|1[0-2])[/.](?:0[1-9]|1[0-9]|2[0-9])|(?:(?!02)(?:0[1-9]|1[0-2])-(?:30))|(?:(?:0[13578]|1[02])-31))"/>
                    </div>
                    <!-- button -->
                    <button type="submit" style="width: 100%" class="btn btn-default">Update Profile</button>
                </form>
            </div>

            <!-- dashboard-->
            <div style="text-align: center">
                <h3>Dashboard</h3>
            </div>
            <div class="well">
                <c:choose>
                    <c:when test="${postsWithC.size() eq 0}">
                        <label>Posts: (not yet)</label><br>
                    </c:when>
                    <c:otherwise>
                        <table class="table table-striped">
                            <thead>
                                <tr>
                                    <th>Post Name</th>
                                    <th>Post Date and Time</th>
                                    <th>Comments</th>
                                    <th> </th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="post" items="${postsWithC.entrySet()}">
                                    <tr>
                                        <td>${post.getKey().getPostName()}</td>
                                        <td>${post.getKey().getPostDateTime()}</td>
                                        <td>${post.getValue()}</td>
                                        <c:url value='/show/post' var="postURL">
                                            <c:param name="postId" value="${post.getKey().getPostId()}"/>
                                        </c:url>
                                        <td><a  class="btn btn-default" href="<c:out value="${postURL}"/>">View</a></td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:otherwise>
                </c:choose>
            </div>

        </div>
    </div>
</div>
<jsp:include page="blog-footer.jsp" />
</body>

</html>
