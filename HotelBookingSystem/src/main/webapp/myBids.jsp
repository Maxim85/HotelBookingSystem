<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Анна
  Date: 05.10.2015
  Time: 16:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="shortcut icon" href="images\icon_hotel.png" type="image/x-icon">
    <title>My bids</title>
    <table>
        <form method="post" action="/app">
            <tr align="center" valign=bottom>
                <td width="370" height="20">
                    <div style="margin-left:160px"><H1>My Bids</H1></div>
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
                    <input type="hidden" name="command" value="exit"/>
                    <input type="image" src="/images/logout.png" width="40" height="40"
                           name="command"/>
                </td>
        </form>
        </tr>
        <tr align="center" valign="top">
            <td></td>
            <td>Home</td>
            <td>Back</td>
            <td>Exit</td>
        </tr>
    </table>
</head>
<body>
<c:choose>
    <c:when test="${not empty actualList}">
        <table border="1px">
            <caption><H2>Actual</H2></caption>
            <tr>
                <td width="100" height="50" align="center">Type</td>
                <td align="center">Capacity</td>
                <td width="100" align="center">Arrival date</td>
                <td align="center">Term</td>
                <td width="100" align="center">Cost per day, $</td>
                <td width="100" align="center">Total cost, $</td>
            </tr>
            <c:forEach var="allBids" items="${actualList}">
                <tr>
                    <td width="100" height="30" align="center">${allBids.type.name}</td>
                    <td align="center">${allBids.type.persons}</td>
                    <td align="center"><fmt:formatDate pattern="yyyy-MM-dd"
                                                       value="${allBids.arrival}"></fmt:formatDate></td>
                    <td align="center">${allBids.term}</td>
                    <td align="center">${allBids.type.price}</td>
                    <td align="center">${allBids.type.price*allBids.term}</td>
                </tr>
            </c:forEach>
        </table>
    </c:when>
    <c:otherwise>
        <div style="margin-left:150px"><h3>Your do not have actually bids</h3></div>
    </c:otherwise>
</c:choose>
<c:choose>
    <c:when test="${not empty historyList}">
        <table border="1px">
            <caption><H2>History</H2></caption>
            <tr>
                <td width="100" height="50" align="center">Type</td>
                <td align="center">Capacity</td>
                <td width="100" align="center">Arrival date</td>
                <td align="center">Term</td>
                <td width="100" align="center">Cost per day, $</td>
                <td width="100" align="center">Total cost, $</td>
            </tr>
            <c:forEach var="allBids" items="${historyList}">
                <tr>
                    <td width="100" height="30" align="center">${allBids.type.name}</td>
                    <td align="center">${allBids.type.persons}</td>
                    <td align="center"><fmt:formatDate pattern="yyyy-MM-dd"
                                                       value="${allBids.arrival}"></fmt:formatDate></td>
                    <td align="center">${allBids.term}</td>
                    <td align="center">${allBids.type.price}</td>
                    <td align="center">${allBids.type.price*allBids.term}</td>
                </tr>
            </c:forEach>
        </table>
    </c:when>
    <c:otherwise>
        <div style="margin-left:150px"><h3>Your do not have historical bids</h3></div>
    </c:otherwise>
</c:choose>
</body>
</html>