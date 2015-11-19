<%--
  Created by IntelliJ IDEA.
  User: Анна
  Date: 03.09.2015
  Time: 9:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Log in</title>
    <link rel="shortcut icon" href="images\icon_hotel.png" type="image/x-icon">
    <table>
        <tr align="center">
            <td><H1 align="center">Access your account</H1></td>
            <td valign="bottom">
                <div style="margin-left:50px"><a href="welcome.jsp"><img src="/images/home_page.png" width="40"
                                                                         height="40"></a></div>
            </td>
        </tr>
        <tr>
            <td></td>
            <td valign="top">
                <div style="margin-left:50px">Home</div>
            </td>
        </tr>
</head>
<body>
<hr/>
<form method="POST" action="/app">
    <table>
        <tr>
            <input type="hidden" name="command" value="login"/>
            <td><span>Login</span></td>
            <td><input type="text" name="login" value=""></td>
        </tr>
        <tr>
            <td><span>Password</span></td>
            <td><input type="password" name="password" value=""></td>
        </tr>
        <tr>
            <td><span style="font-size:10px">Remember me</span></td>
            <td><input type="checkbox" name="remember"/></td>
        </tr>
        <tr>
            <align
            = "center">
            <td><input type="submit" value="Enter"/></td>
        </tr>
        <tr>
            <td><span style="font-size:14px">No account </span><a href="registration.jsp">Start here</a></td>
        </tr>
    </table>
</form>
<hr/>
</body>
</html>
