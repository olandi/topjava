<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="date" uri="LocalDateTimePrinter" %>
<%--<%@ include file="colors.css" %>--%>
<html>
<head>
    <title>Meals</title>
    <link href="colors.css" rel="stylesheet" type="text/css">
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>

<table>
    <thead>
    <tr>
        <th>dateTime</th>
        <th>description</th>
        <th>calories</th>
        <th>exceed</th>
    </tr>
    </thead>

    <c:forEach var="meal" items="${requestScope.meals}">
        <tr class = ${meal.exceed? "red" : "green" }>
            <td>${date:PrintDate(meal.dateTime)}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td>${meal.exceed}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>

