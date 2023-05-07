<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login Page</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <link rel="shortcut icon" href="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBzdGFuZGFsb25lPSJubyI/Pgo8IURPQ1RZUEUgc3ZnIFBVQkxJQyAiLS8vVzNDLy9EVEQgU1ZHIDIwMDEwOTA0Ly9FTiIKICJodHRwOi8vd3d3LnczLm9yZy9UUi8yMDAxL1JFQy1TVkctMjAwMTA5MDQvRFREL3N2ZzEwLmR0ZCI+CjxzdmcgdmVyc2lvbj0iMS4wIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciCiB3aWR0aD0iMjU2LjAwMDAwMHB0IiBoZWlnaHQ9IjI1Ni4wMDAwMDBwdCIgdmlld0JveD0iMCAwIDI1Ni4wMDAwMDAgMjU2LjAwMDAwMCIKIHByZXNlcnZlQXNwZWN0UmF0aW89InhNaWRZTWlkIG1lZXQiPgoKPGcgdHJhbnNmb3JtPSJ0cmFuc2xhdGUoMC4wMDAwMDAsMjU2LjAwMDAwMCkgc2NhbGUoMC4xMDAwMDAsLTAuMTAwMDAwKSIKZmlsbD0iIzAwMDAwMCIgc3Ryb2tlPSJub25lIj4KPHBhdGggZD0iTTkzMCAyNTQyIGMtMzUgLTE5IC01MDQgLTMxNyAtNTkwIC0zNzQgLTU4IC0zOSAtODcgLTc2IC0xMTYgLTE0NgpsLTI0IC01NyAwIC02NzMgYzAgLTczNSAtMiAtNzEyIDU4IC03NzUgMzggLTQwIDEzMiAtODYgMjExIC0xMDMgMTQ1IC0zMCAzNDUKLTEwIDM4NyA0MCAyMiAyNSAyMiAyOCAyNiA1ODMgNiA3NzAgLTIyIDcwMCAzOTggOTgzIDI0OCAxNjcgMjc2IDE4OSAyODcgMjIyCjI0IDc0IC0xNiAxMzggLTg3IDEzOCAtNDAgMCAtODkgLTI4IC0zODUgLTIyNyAtNDA3IC0yNzIgLTUzNiAtMzQzIC02MTkgLTM0MwotNTAgMCAtNzkgMjggLTc5IDc2IDAgNjYgMjQgOTcgMTIzIDE1OSA1MCAzMCAxNTMgOTUgMjI5IDE0NCBsMTM4IDg4IDMxIC0yOQpjNDQgLTM5IDkzIC0zOCAxMzMgMSAyOSAyOSAyOSAzMSAyOSAxNDAgMCAxMDAgLTIgMTEyIC0yMiAxMzYgLTMxIDM1IC04MyA0MgotMTI4IDE3eiIvPgo8cGF0aCBkPSJNMTczMCAyMjAyIGMtNDcgLTIyIC01OTIgLTQwMiAtNjM4IC00NDYgLTYyIC01OCAtODkgLTExMSAtMTAyIC0xOTIKLTcgLTQ3IC0xMCAtMjc5IC04IC03MDAgMyAtNjIzIDMgLTYyOSAyNSAtNjY5IDkxIC0xNzAgMzc0IC0yNDQgNTg0IC0xNTQgNzcKMzQgNzE4IDUxMSA3NTQgNTYyIDEzIDE5IDE1IDExMCAxNSA3MDAgbDAgNjc4IC0yNSAyNCBjLTE4IDE5IC0zNSAyNSAtNjkgMjUKLTQwIDAgLTU0IC04IC0xNDMgLTc1IC02MTIgLTQ2OSAtNjgxIC01MTMgLTgxMyAtNTIzIC02NyAtNCAtNzAgLTQgLTEwMSAyNwotMjYgMjcgLTMxIDM3IC0yNiA2NiAzIDE4IDEzIDQ1IDIyIDU4IDkgMTQgMTA5IDkwIDIyMyAxNzAgMjc1IDE5MiAyNDYgMTc2CjI3NyAxNDcgMzkgLTM3IDEwOCAtMzUgMTQxIDMgMjIgMjYgMjQgMzYgMjQgMTM4IDAgMTA5IDAgMTExIC0yOSAxNDAgLTI5IDI5Ci03NCAzNyAtMTExIDIxeiBtNDQwIC04MDQgbC0xIC05MyAtMjQwIC0xODUgYy0xMzIgLTEwMiAtMjQ0IC0xODYgLTI0OSAtMTg4Ci02IC0yIC0xMCAzMyAtMTAgODcgbDAgOTAgMjQzIDE5MCBjMTMzIDEwNCAyNDUgMTkwIDI1MCAxOTAgNCAxIDcgLTQxIDcgLTkxeiIvPgo8L2c+Cjwvc3ZnPgo=" />
    <style>
        /* Set black background color, white text and some padding */
        footer {
            background-color: #555;
            color: white;
            padding: 15px;
        }
        /* Remove the navbar's default rounded borders and increase the bottom margin */
        .navbar {
            margin-bottom: 0;
            border-radius: 0;
        }

        /* Remove the jumbotron's default bottom margin */
        .jumbotron {
            margin-bottom: 0;
        }

        .btn-block.my {
            background-color: #222222;
            color: #f1f1f1;
        }

        .text-center.my-login {
            padding: 100px 140px 0 140px;
        }

        #login-check {
            padding: 0;
        }

        .justify-content-center.my-login {
            height: 40px;
            vertical-align: middle;
            text-align: center;
            padding-bottom: 10px;
            padding-top: 10px;
        }

        .text-center.my-login-r {
            margin-top: 15px;
        }

        /* Set height of the grid so .sidenav can be 100% (adjust as needed) 600px*/
        .row.content {height: 640px}

        /* Set gray background color and 100% height */
        .sidenav {
            padding-top: 20px;
            background-color: #f1f1f1;
            height: 100%;
        }

        /* On small screens, set height to 'auto' for sidenav and grid */
        @media screen and (max-width: 767px) {
            .sidenav {
                height: auto;
                padding: 15px;
            }
            .row.content {height:auto;}
        }
    </style>
</head>
<c:url value='/redirect/registration' var="registrationURL"/>
<c:url value='/redirect/login' var="loginURL"/>
<c:url value='/redirect/forgot-password' var="fpassURL"/>
<body>
<header>
    <nav class="navbar navbar-inverse">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand" href="<c:out value="${loginURL}"/>">Too Simple Blog</a>
            </div>
            <div class="collapse navbar-collapse" id="myNavbar">
                <ul class="nav navbar-nav navbar-right">
                    <li>
                        <a href="<c:out value="${registrationURL}"/>">
                            <span class="glyphicon glyphicon-log-in"></span> Registration</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</header>

<div class="container-fluid text-center">
    <div class="row content">
        <div class="col-sm-3 sidenav">
        </div>
        <div class="col-sm-6 text-center my-login">
            <h1>Log In</h1>
            <c:if test="${not empty ExceptionMessage}">
            <div class="alert alert-danger">
                <label >${ExceptionMessage}</label>
            </div>
            </c:if>
            <c:if test="${not empty SuccessMessage}">
                <div class="alert alert-success">
                    <label >${SuccessMessage}</label>
                </div>
            </c:if>
            <form name="username" action="<c:url value='/login' />" method="POST">
                <div class="form-group text-left">
                    <label for="loginUserName">Email: </label>
                    <input type="text" value="" class="form-control" name="loginUserName" required placeholder="Enter email" id="loginUserName" pattern="[a-zA-Z0-9.-]+@[a-zA-Z0-9.-]+\.[a-z]{2,}$"/>
                </div>
                <div class="form-group  text-left">
                    <label for="loginPassword">Password: </label>
                    <input type="password" value="" class="form-control" name="loginPassword" required placeholder="Enter password" id="loginPassword"/>
                </div>

                <div class="row mb-4">
                    <div class="col-md-6 d-flex justify-content-center">
                        <!-- Checkbox -->
                        <div class="checkbox">
                            <input   type="checkbox" value="" id="loginCheck"/> <!-- class="form-check-input" -->
                            <label id="login-check" for="loginCheck">Remember me</label> <!-- class="form-check-label"-->
                        </div>
                    </div>

                    <div class="col-md-6 d-flex justify-content-center my-login">
                        <!-- Simple link -->
                        <a href="<c:out value="${fpassURL}"/>">Forgot password?</a>
                    </div>
                </div>

                <!-- button -->
                <button type="submit" class="btn btn-block my text-left">Login</button> <!--btn-default-->

                <!-- register -->
                <div class="text-center my-login-r">
                    <p>Not a member? <a href="<c:out value="${registrationURL}"/>">Register</a></p>
                </div>
            </form>

        </div>
        <div class="col-sm-3 sidenav">
        </div>
    </div>
</div>
<jsp:include page="blog-footer.jsp" />
</body>
</html>
