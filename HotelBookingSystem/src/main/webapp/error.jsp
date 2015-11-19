<%--
  Created by IntelliJ IDEA.
  User: Анна
  Date: 03.09.2015
  Time: 16:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
    <link rel="shortcut icon" href="/images/icon_hotel.png" type="image/x-icon">
</head>
<body>
<table>
    <tr align="center">
        <td><H1 align="center">Error</H1></td>
        <td valign="bottom">
            <div style="margin-left:50px"><a href="welcome.jsp"><img src="/images/home_page.png" width="40" height="40"></a>
            </div>
        </td>
        <form>
            <td valign="bottom">
                <div style="margin-left:30px"><input type="image" src="/images/reload.png" value="Back" width="40"
                                                     height="40"
                                                     onClick="history.go(-1);return true;"></div>
            </td>
        </form>
    </tr>
    <tr>
        <td></td>
        <td valign="top">
            <div style="margin-left:50px">Home</div>
        </td>
        <td valign="top">
            <div style="margin-left:33px">Back</div>
        </td>
    </tr>
</table>
<h3></h3>
<hr/>
<jsp:expression>
    (request.getAttribute("errorMessage") != null)
    ? (String) request.getAttribute("errorMessage")
    : "unknown error"</jsp:expression>
<hr/>
</body>
</html>
