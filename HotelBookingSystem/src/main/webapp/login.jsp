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
    <title>Login</title>
</head>
<body><hr/><form name="loginForm" method="POST"
            action="controller">
  <input type="hidden" name="command" value="login" />
  Login:<br/>
  <input type="text" name="login" value=""><br/>
  Password:<br/>
  <input type="password" name="password" value="">
  <br/>
  <input type="submit" value="Enter">
</form><hr/>


</body>
</html>
