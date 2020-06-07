<%--
  Created by IntelliJ IDEA.
  User: local_remote6
  Date: 07.06.2020
  Time: 22:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<html>
<head>
    <title>Edit Meal</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
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
                <tr style="color: forestgreen">
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
    <input type="datetime-local" name="dateTime" value="<javatime:format value="${mealToEdit.dateTime}" pattern="dd-MM-yyyy HH:mm" />">
    <input type="text" name="description" value="${mealToEdit.description}">
    <input type="number" name="calories" value="${mealToEdit.calories}">
    <input type="hidden" name="id" value="${mealToEdit.id}">
    <input type="submit" name="action" value="Update">
</form>
</body>
</html>
