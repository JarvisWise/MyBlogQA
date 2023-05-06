<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <style>
        .alert {border-radius: 0 !important;}
        .row.content.my-c {background: #f1f1f1;}
        body {background-color: #f1f1f1}
        td.desc-my {max-width: 300px;}
        td.grade-my {max-width: 150px;}
        th.alert.alert-info {width: 200px;}
        .jumbotron {margin-bottom: 0;}

        footer {
            background-color: #555;
            color: white;
            padding: 15px;
        }

        .navbar {
            margin-bottom: 0;
            border-radius: 0;
        }

        .col-xs-8.col-xs-offset-2.text-left.my {
            background: #fff;
            min-height: calc(100vh - 112px);
        }

        .row.content {
            height: available;
            min-height: 640px;
            /*padding-top: 20px;*/
            background-color: #ffffff;
        }

        div.well {
            padding: 9px 19px 9px;
            margin: 5px 0 5px;
        }

        /*div.my {
            height: 100% !important;
        }*/

        .table-bordered.tb-my {
            background-color: #ffffff;
            padding: 0;
            margin: 0;
            /*text-align: center;*/
            border-radius: 15px !important;
        }

        .table-bordered.tb-my.cp,
        .table-bordered.tb-my.cp td,
        .table-bordered.tb-my.cp th,
        .table-bordered.tb-my.cp tr,
        .table-bordered.tb-my.cp thead,
        .table-bordered.tb-my.cp tbody
        {
            border: none !important;
        }

        .profile {
            padding: 0;
            margin: 0 auto;
            text-align: center;
        }

        .form-group.input-group.my,
        .form-group.my,
        form.my {margin-bottom: 0;}

        .sidenav {
            padding-top: 20px;
            background-color: #f1f1f1;
            max-height: 10000px; /*find way*/
            min-height: 640px;
        }

        @media screen and (max-width: 767px) {
            .sidenav {
                height: auto;
                padding: 15px;
            }
            .row.content {height:auto;}
        }
    </style>
</head>

<%
    String username=(String)session.getAttribute("current_username");
    String userId=(String)session.getAttribute("current_user_id");
%>

<c:url value='/logout' var="logoutURL"/>
<c:url value='/redirect/add/post' var="addPostURL"/>
<c:url value='/show/blog-profile' var="currentUserURL">
    <c:param name="userId" value="<%=userId%>"/>
</c:url>
<c:url value='/show/post-list' var="postListURL"/>

<header>
    <nav class="navbar navbar-inverse">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand" href="<c:out value="${postListURL}"/>">Too Simple Blog</a>
            </div>

            <div class="collapse navbar-collapse" id="myNavbar">
                <ul class="nav navbar-nav">
                    <li><a href="<c:out value="${addPostURL}"/>">Create New Post</a></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li>
                        <a href="<c:out value="${currentUserURL}"/>">
                            <span class="glyphicon glyphicon-user"></span> <%=username%></a>
                    </li>
                    <li>
                        <a href="<c:out value="${logoutURL}"/>">
                            <span class="glyphicon glyphicon-log-out"></span> Logout</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</header>
