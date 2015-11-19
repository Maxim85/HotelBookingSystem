<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.booking.model.Type" %>
<%--
  Created by IntelliJ IDEA.
  User: Анна
  Date: 19.11.2015
  Time: 11:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit users database</title>
    <table>
        <form method="post" action="/app">
            <input type="hidden" name="command" value="exit"/>
            <tr align="center" valign=bottom>
                <td width="950" height="20">
                    <div style="margin-left:150px"><H1>Edit users database</H1></div>
                </td>
                <td valign="bottom" width="50" height="20">
                    <a href="welcome.jsp"><img src="/images/home_page.png" width="40"
                                               height="40"></a>
                </td>
                <td valign="bottom" width="50" height="20">
                    <input type="image" src="/images/reload.png" value="Back"
                           width="40"
                           height="40"
                           onClick="history.go(-1);return true;">
                </td>
                <td valign="bottom" width="50" height="20">
                    <input type="image" src="/images/logout.png" width="40" height="40"
                           name="command"/>
                </td>
            </tr>
            <tr align="center" valign="top">
                <td></td>
                <td>Home</td>
                <td>Back</td>
                <td>Exit</td>
            </tr>
        </form>
    </table>
</head>
<body>
<table>
    <caption><H2>All users</H2></caption>
    <tr>
        <td>
            <table border="1px">
                <c:choose>
                    <c:when test="${not empty fullListUsers}">
                        <tr align="center" valign="middle">
                            <td width="110" align="center">ID</td>
                            <td width="198" height="50" align="center">User's login</td>
                            <td width="157" align="center">User's name</td>
                            <td width="181" align="center">User's password</td>
                            <td width="185" align="center">User's mail</td>
                        </tr>
                        <c:forEach var="Users" items="${fullListUsers}">
                            <tr>
                            <td height="30" align="center">${Users.id}</td>
                            <td align="center">${Users.login}</td>
                            <td align="center">${Users.name}</td>
                            <td align="center">${Users.password}</td>
                            <td align="center" height="30">${Users.mail}</td>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr align="center">
                            <td align="center" height="80" width="856"><h2>Your do not have users</h2></td>
                        </tr>
                        <%--<div style="margin-left:450px"><h2>Your do not have bids</h2></div>--%>
                    </c:otherwise>
                </c:choose>
                </tr>
            </table>
        </td>
        <td valign="top">
            <table border="1px">
                <form method="post" action="/app">
                    <tr>
                        <td width="100" height="50" align="center">Select ID</td>
                        <td align="center">Action</td>
                    </tr>
                    <tr>
                        <td width="100" height="30" align="center"><select name="id" width="120" style="width: 120px">
                            <c:set var="users" value="${listWithOutActualAdmin}"/>
                            <c:forEach var="user" items="${users}">
                                <option value="${user.id}">${user.id}</option>
                            </c:forEach>
                        </select>
                        </td>
                        <td align="center">
                            <input type="hidden" name="command" value="deleteUsers"/>
                            <button type="submit" style="width:100px; height:25px">Delete</button>
                        </td>
                    </tr>
                </form>
            </table>
        <td>
    <tr>
        <table border="1px">
            <form method="post" action="/app">
                <tr>
                    <td width="244" height="50" align="center">Set client's login</td>
                    <td width="244" align="center">Set client's password</td>
                    <td width="244" align="center">Set client's name</td>
                    <td width="244" align="center">Set client's mail</td>
                    <td align="center">Action</td>
                </tr>
                <tr>
                    <td height="30"><input type="text" name="login" value="" size="30"></td>
                    <td><input type="text" name="password" value="" size="30"></td>
                    <td><input type="text" name="name" value="" size="30"></td>
                    <td><input type="text" name="mail" value="" size="30"></td>
                    <td align="center" valign="middle">
                        <input type="hidden" name="command" value="addUsers"/>
                        <button type="submit" style="width:100px; height:25px">Add</button>
                    </td>
                </tr>
            </form>
        </table>
    </tr>
    <tr>
        <table border="1px">
            <form method="post" action="/app">
                <tr>
                    <td width="100" height="50" align="center">Select ID</td>
                    <td width="118" height="50" align="center">Set client's login</td>
                    <td width="244" align="center">Set client's password</td>
                    <td width="244" align="center">Set client's name</td>
                    <td width="244" align="center">Set client's mail</td>
                    <td align="center">Action</td>
                </tr>
                <tr>
                    <td width="100" height="30" align="center"><select name="id" width="120" style="width: 120px">
                        <c:set var="users" value="${fullListUsers}"/>
                        <c:forEach var="user" items="${users}">
                            <option value="${user.id}">${user.id}</option>
                        </c:forEach>
                    </select>
                    <td height="30"><input type="text" name="login" value="" size="12"></td>
                    <td><input type="text" name="password" value="" size="30"></td>
                    <td><input type="text" name="name" value="" size="30"></td>
                    <td><input type="text" name="mail" value="" size="30"></td>
                    <td align="center" valign="middle">
                        <input type="hidden" name="command" value="updateUsers"/>
                        <button type="submit" style="width:100px; height:25px">Update</button>
                    </td>
                </tr>
            </form>
        </table>
    </tr>
</table>
</body>
</html>