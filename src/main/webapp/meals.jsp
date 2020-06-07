<%--
  Created by IntelliJ IDEA.
  User: local_remote6
  Date: 07.06.2020
  Time: 1:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>

<html>
<head>
    <title>Meals</title>
</head>
<body>
<table>
    <thead>
        <tr>
            <td>Date/Time</td>
            <td>Description</td>
            <td>Calories</td>
        </tr>
    </thead>
    <tbody>
    <jsp:useBean id="meals" scope="request" type="java.util.List"/>
    <c:forEach var="meal" items="${meals}">
                <c:choose>
                    <c:when test="${meal.excess}">
                        <tr style="color: red">
                    </c:when>
                    <c:otherwise>
                        <tr style="color: green">
                    </c:otherwise>
                </c:choose>
            <td><javatime:format value="${meal.dateTime}" pattern="dd-MM-yyyy HH:mm" /></td>
            <td><c:out value="${meal.description}" /></td>
            <td><c:out value="${meal.calories}" /></td>
            <td>
                <form method="post">
                    <input type="hidden" name="id" value="<c:out value="${meal.id}" />">
                    <input type="submit" name="action" value="Edit"/>
                </form>
            </td>
            <td>
                <form method="post">
                    <input type="hidden" name="id" value="<c:out value="${meal.id}" />">
                    <input type="submit" name="action" value="Delete"/>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<form method="post">
    <input type="datetime-local" name="dateTime" value="<javatime:format value="${now}" pattern="dd-MM-yyyy HH:mm" />">
    <input type="text" name="description" value="">
    <input type="number" name="calories" value="">
    <input type="hidden" name="id">
    <input type="submit" name="action" value="Add">
</form>
</body>
</html>
