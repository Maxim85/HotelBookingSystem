<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Анна
  Date: 03.09.2015
  Time: 15:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.booking.model.Type" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <form method="POST" action="/app">
        <input type="hidden" name="command" value="exit"/>
        <title>Welcome</title>
        <link rel="shortcut icon" href="/images/icon_hotel.png" type="image/x-icon">
        <table>
            <tr align="center">
                <td valign="bottom" align="center" width="300"><H1>Welcome ${client.name}</H1></td>
                <td valign="bottom" width="50" height="20">
                    <a href="welcome.jsp"><img src="/images/home_page.png" width="40"
                                               height="40"></a>
                </td>
                <form>
                    <td valign="bottom" width="50" height="20">
                        <input type="image" src="/images/reload.png" value="Back"
                               width="40"
                               height="40"
                               onClick="history.go(-1);return true;">
                    </td>
                </form>
                <td valign="bottom" width="50" height="20">
                    <a href="myBids.jsp"><img src="/images/stack.png" width="40"
                                              height="40"></a>
                </td>
                <form method="post" action="/app">
                    <td valign="bottom" width="50" height="20">
                        <input type="image" src="/images/logout.png" width="40" height="40"
                               name="command" value = "exit"/>
                    </td>
                </form>
            </tr>
            <tr>
                <td valign="top" align="center"><H2>Create a new bid</H2></td>
                <td valign="top" align="center">Home</td>
                <td valign="top" align="center">Back</td>
                <td valign="top" align="center">Bids</td>
                <td valign="top" align="center">Exit</td>
            </tr>
        </table>
    </form>
</head>
<body>
<form method="POST" action="/app">
    <input type="hidden" name="command" value="bid"/>
    <table>
        <tr>
            <td><span>Arrival</span></td>
            <td><input type="date" name="arrivalDate"></td>
        </tr>
        <tr>
            <td><span>Type</span></td>
            <td>
                <select name="type">
                    <%--jsp:useBean id="types" class="Type"/--%>
                    <%--c:forEach var="type" items="${types.enumValues}"--%>
                    <c:set var="types" value="<%=Type.values()%>"/>
                    <c:forEach var="type" items="${types}">
                        <option value="${type}">${type.name} for ${type.persons} person, cost per day ${type.price}$
                        </option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td><span>Term</span></td>
            <td><input type="text" name="term" value=""></td>
        </tr>

        <tr>
            <td><input type="submit" value="Send"/></td>
        </tr>
    </table>
</form>
</body>
</html>