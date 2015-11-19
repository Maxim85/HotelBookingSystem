<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.booking.model.Type" %>
<%--
  Created by IntelliJ IDEA.
  User: Анна
  Date: 28.10.2015
  Time: 15:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Forming the bills</title>
    <link rel="shortcut icon" href="images/icon_hotel.png" type="image/x-icon">
    <title>My bids</title>
    <table>
        <form method="post" action="/app">
            <input type="hidden" name="command" value="exit"/>
            <tr align="center" valign=bottom>
                <td width="1200" height="20">
                    <div style="margin-left:150px"><H1>Forming the bills</H1></div>
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
<table border="1px">
    <caption><H2>Number of apartments available</H2></caption>
    <tr>
        <td width="100" height="50" align="center">Type</td>
        <td align="center">Capacity</td>
        <td width="100" align="center">Cost per day, $</td>
        <td width="100" align="center">Available</td>
    </tr>
    <c:forEach var="type" items="<%=Type.values()%>">
        <tr>
            <td width="100" height="15" align="center">${type.name}</td>
            <td align="center">${type.persons}</td>
            <td width="100" align="center">${type.price}</td>
            <td width="100" align="center">${type.numberApartments}</td>
        </tr>
    </c:forEach>
</table>

<table>
    <caption><H2>Actual bids / Proposed bills</H2></caption>
    <tr>
        <td>
            <table border="1px">
                <c:choose>
                    <c:when test="${not empty actualBids}">
                        <tr>
                            <td align="center">ID</td>
                            <td width="100" height="50" align="center">Client's name</td>
                            <td align="center">Login</td>
                            <td align="center">Type</td>
                            <td align="center">Capacity</td>
                            <td align="center">Arrival date</td>
                            <td align="center">Term</td>
                            <td align="center">Cost per day, $</td>
                        </tr>
                        <c:forEach var="Bids" items="${actualBids}">
                            <tr>
                            <td height="30" align="center">${Bids.id}</td>
                            <td align="center">${Bids.user.name}</td>
                            <td align="center">${Bids.user.login}</td>
                            <td align="center">${Bids.type.name}</td>
                            <td align="center">${Bids.type.persons}</td>
                            <td align="center"><fmt:formatDate pattern="yyyy-MM-dd"
                                                               value="${Bids.arrival}"></fmt:formatDate></td>
                            <td align="center">${Bids.term}</td>
                            <td align="center">${Bids.type.price}</td>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr align="center">
                            <td  align="center" height="80"  width="856"><h2>Your do not have actual bids</h2></td>
                        </tr>
                    </c:otherwise>
                </c:choose>
                </tr>
            </table>
        </td>
        <td valign="top">
            <table border="1px">
                <form method="post" action="/app">
                    <tr>
                        <td width="50" height="50" align="center">ID</td>
                        <td align="center">Login</td>
                        <td align="center">Arrival date</td>
                        <td align="center">Type</td>
                        <td align="center">Term</td>
                        <td width="100" align="center">Send the bill</td>
                    </tr>
                    <td height="30"><select name="id">
                        <c:set var="bids" value="${actualBids}"/>
                        <c:forEach var="bid" items="${bids}">
                            <option value="${bid.id}">${bid.id}</option>
                        </c:forEach>
                    </select>
                    </td>
                    <td><select name="login">
                        <c:set var="Clients" value="${onlyClient}"/>
                        <c:forEach var="user" items="${Clients}">
                            <option value="${user.login}">${user.login}</option>
                        </c:forEach>
                    </select>
                    </td>
                    <td><input type="date" name="arrivalDate" size="2"></td>
                    <td>
                        <select name="type">
                            <c:set var="types" value="<%=Type.values()%>"/>
                            <c:forEach var="type" items="${types}">
                                <option value="${type}">${type.name} for ${type.persons} person, cost per
                                    day ${type.price}$
                                </option>
                            </c:forEach>
                        </select>
                    </td>
                    <td><input type="text" name="term" value="" size="5"></td>
                    <td>
                        <input type="hidden" name="command" value="sendBills"/>
                        <button type="submit" style="width:100px; height:25px">Enter</button>
                    </td>
                </form>
            </table>
        </td>
    </tr>
</table>
</form>
</body>
</html>
