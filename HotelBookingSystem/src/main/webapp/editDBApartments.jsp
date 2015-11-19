<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.booking.model.Type" %>
<%--
  Created by IntelliJ IDEA.
  User: Анна
  Date: 19.11.2015
  Time: 9:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit apartments database</title>
    <table>
        <form method="post" action="/app">
            <input type="hidden" name="command" value="exit"/>
            <tr align="center" valign=bottom>
                <td width="950" height="20">
                    <div style="margin-left:150px"><H1>Edit apartments database</H1></div>
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
    <caption><H2>All apartments</H2></caption>
    <tr>
        <td>
            <table border="1px">
                <c:choose>
                    <c:when test="${not empty fullListApartments}">
                        <tr align="center" valign="middle">
                            <td height="50" align="center">ID</td>
                            <td align="center">Type</td>
                            <td align="center">Capacity</td>
                            <td align="center">Cost per day, $</td>
                            <td align="center">Check out date</td>
                        </tr>
                        <c:forEach var="Apartments" items="${fullListApartments}">
                            <tr>
                            <td width="105" height="30" align="center">${Apartments.id}</td>
                            <td width="203" align="center">${Apartments.type.name}</td>
                            <td width="130"align="center">${Apartments.type.persons}</td>
                            <td width="130"align="center">${Apartments.type.price}</td>
                            <td width="265" align="center"><fmt:formatDate pattern="yyyy-MM-dd"
                                                                           value="${Apartments.checkOutDate}"></fmt:formatDate></td>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr align="center">
                            <td align="center" height="80" width="856"><h2>Your do not have apartments</h2></td>
                        </tr>
                        <%--<div style="margin-left:450px"><h2>Your do not have bills</h2></div>--%>
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
                            <c:set var="apartments" value="${fullListApartments}"/>
                            <c:forEach var="apartment" items="${apartments}">
                                <option value="${apartment.id}">${apartment.id}</option>
                            </c:forEach>
                        </select>
                        </td>
                        <td align="center">
                            <input type="hidden" name="command" value="deleteApartments"/>
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
                    <td colspan="3" width="100" height="50" align="center">Select client's login</td>
                    <td align="center" colspan="3">Select type</td>
                    <td align="center">Select term</td>
                    <td align="center" colspan="3">Select check out of day</td>
                    <td align="center">Action</td>
                </tr>
                <tr align="center">
                    <td colspan="3">
                        <select name="login" width="315" style="width: 315px">
                            <c:set var="Clients" value="${fullListClients}"/>
                            <c:forEach var="user" items="${Clients}">
                                <option value="${user.login}">${user.login}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td colspan="3">
                        <select name="type" width="266" style="width: 266px">
                            <c:set var="types" value="<%=Type.values()%>"/>
                            <c:forEach var="type" items="${types}">
                                <option value="${type}">${type.name} for ${type.persons} person, cost per
                                    day ${type.price}$
                                </option>
                            </c:forEach>
                        </select>
                    </td>
                    <td width="50"><input type="text" name="term" value="" size="6"></td>
                    <td colspan="3"><input type="date" name="checkOutDate" width="320" style="width: 320px">
                    </td>
                    <td align="center" valign="middle">
                        <input type="hidden" name="command" value="addApartments"/>
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
                    <td width="100" height="50" align="center">Select ID apartment</td>
                    <td colspan="3" align="center">Select client's login</td>
                    <td align="center" colspan="3">Select type</td>
                    <td align="center">Select term</td>
                    <td align="center" colspan="3">Select check out of day</td>
                    <td align="center">Action</td>
                </tr>
                <tr align="center">
                    <td><select name="id" width="109" style="width: 109px">
                        <c:set var="apartments" value="${fullListApartments}"/>
                        <c:forEach var="apartment" items="${apartments}">
                            <option value="${apartment.id}">${apartment.id}</option>
                        </c:forEach>
                    </select>
                    </td>
                    <td colspan="3">
                        <select name="login" width="200" style="width: 200px">
                            <c:set var="Clients" value="${fullListClients}"/>
                            <c:forEach var="user" items="${Clients}">
                                <option value="${user.login}">${user.login}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td colspan="3">
                        <select name="type" width="266" style="width: 266px">
                            <c:set var="types" value="<%=Type.values()%>"/>
                            <c:forEach var="type" items="${types}">
                                <option value="${type}">${type.name} for ${type.persons} person, cost per
                                    day ${type.price}$
                                </option>
                            </c:forEach>
                        </select>
                    </td>
                    <td width="50"><input type="text" name="term" value="" size="6"></td>
                    <td colspan="3"><input type="date" name="checkOutDate" width="320" style="width: 320px">
                    </td>
                    <td align="center" valign="middle">
                        <input type="hidden" name="command" value="updateApartments"/>
                        <button type="submit" style="width:100px; height:25px">Update</button>
                    </td>
                </tr>
            </form>
        </table>
    </tr>
</table>
</body>
</html>
