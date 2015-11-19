<%--
  Created by IntelliJ IDEA.
  User: Анна
  Date: 15.09.2015
  Time: 11:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin page</title>
    <link rel="shortcut icon" href="images/icon_hotel.png" type="image/x-icon">

    <table>
        <tr align="center" valign=bottom>
            <td><H1>Welcome ${admin.name}</H1></td>
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
            <form method="post" action="/app">
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
    <style>
        li {
            list-style-type: none;
        }

        ul {
            margin-left: 0;
            padding-left: 0;
        }
    </style>
</head>
<body>
<menu>
    <li>
        <form method="post" action="/app">
            <input type="hidden" name="command" value="forming"/>
            <button type="submit" style="width:300px; height:50px">Forming the bills</button>
        </form>
    </li>
    <li>
        <form method="post" action="/app">
            <input type="hidden" name="command" value="editDBUsers"/>
            <button type="submit" style="width:300px; height:50px">Edit database of users</button>
        </form>
    </li>
    <li>
        <form method="post" action="/app">
            <input type="hidden" name="command" value="editDBApartments"/>
            <button type="submit" style="width:300px; height:50px">Edit database of apartments</button>
        </form>
    </li>

    <li>
        <form method="post" action="/app">
            <input type="hidden" name="command" value="editDBBids"/>
            <button type="submit" style="width:300px; height:50px">Edit database of bids</button>
        </form>
    </li>
    <li>
        <form method="post" action="/app">
            <input type="hidden" name="command" value="editDBBills"/>
            <button type="submit" style="width:300px; height:50px">Edit database of bills</button>
        </form>
    </li>
</menu>
</body>
</html>
