<%@ page import="model.Event" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Armen
  Date: 05.09.2022
  Time: 16:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Events page</title>
</head>
<body>

<%
    List<Event> events = (List<Event>) request.getAttribute("events");


%>

<table border="1">
    <tr>
        <th>id</th>
        <th>name</th>
        <th>place</th>
        <th>osOnline</th>
        <th>event type</th>
        <th>price</th>
        <th>event date</th>
    </tr>
    <%
        for (Event event : events) { %>
    <tr>
        <td><%=event.getId()%>
        </td>
        <td><%=event.getName()%>
        </td>
        <td><%=event.getPlace()%>
        </td>
        <td><%=event.isOnline()%>
        </td>
        <td><%=event.getEventType().name()%>
        </td>
        <td><%=event.getPrice()%>
        </td>
        <td><%=event.getEventDate()%>
        </td>
    </tr>
    <% } %>

</table>

</body>
</html>
