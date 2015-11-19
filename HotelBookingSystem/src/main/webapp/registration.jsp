<%--
  Created by IntelliJ IDEA.
  User: Анна
  Date: 17.09.2015
  Time: 13:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Registration Form</title>
  <link rel="shortcut icon" href="images/icon_hotel.png" type="image/x-icon">
  <H1>Registration Form</H1>
</head>
<body>
<form method="POST" action="/app">
  <table>
    <tr>
      <input type="hidden" name="command" value="registration"/>
      <td><span>Name</span></td>
      <td><input type="text" name="name" value=""></td>
    </tr>
    <tr>
      <td><span>Login</span></td>
      <td><input type="text" name="login" value=""></td>
    </tr>
    <tr>
      <td><span>Password</span></td>
      <td><input type="password" name="password" value=""></td>
    </tr>
    <tr>
      <td><span>E-mail</span></td>
      <td><input type="text" name="mail" value=""></td>
    </tr>
    <tr>
      <td> <input type="submit" value="Enter"></td>
    </tr>
  </table>
</form>
</body>
</html>
