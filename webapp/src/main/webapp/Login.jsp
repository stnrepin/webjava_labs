<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
    <form action="LoginProcessor">
        <label for="username">Username:</label>
        <br>
        <input id="username" name="userName" autocomplete="off" />
        <br>
        <label for="color">Your color: </label>
        <br>
        <input id="color" name="userColor" autocomplete="off" />
        <br><br>
        <input type="submit" value="Login" />
    </form>
</body>
</html>
