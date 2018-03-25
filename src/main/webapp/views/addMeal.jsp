<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF8" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form method="post" action="meals">
    <table>
        <tr>
            <td>ID</td>
            <td><input type="text" name="id" readonly value="${requestScope.meal.id}"></td>
        </tr>

        <tr>
            <td>dateTime</td>
            <td><input type="datetime-local" name="dateTime" value="${requestScope.meal.dateTime}"></td>
        </tr>

        <tr>
            <td>description</td>
            <td><input type="text" name="description" value="${requestScope.meal.description}"></td>
        </tr>

        <tr>
            <td>calories</td>
            <td><input type="text" name="calories" value="${requestScope.meal.calories}"></td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" name="OK">
            </td>
        </tr>

    </table>




</form>
</body>
</html>
