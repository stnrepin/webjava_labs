<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create a filter for medicines</title>
</head>
<body>
    <form action="MedicineList.jsp">
        <label for="max-price">Minimal price:</label><br>
        <input type="number" id="max-price" name="max-price" autocomplete="off" min="0" value="0">
    </form>
</body>
</html>
