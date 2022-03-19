<%@ page import="java.util.Optional" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.time.Instant" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%!
  /**
   * Ищет куки по имени и возвращает его значение.
   * @param request Объект запроса.
   * @param name имя куки.
   * @return Значение куки, если оно найдено.
   */
  Optional<String> findCookieValue(HttpServletRequest request, String name) {
    return Arrays.stream(
      request.getCookies())
            .filter(x -> x.getName().equals(name))
            .findFirst()
            .map(Cookie::getValue);
  }
%>
<%
  // Находит значения переменных, сохраненные в куки.
  String userName = findCookieValue(request, "user.name").orElse("Anon");
  String userColor = findCookieValue(request, "user.color").orElse("black");
  // Находит значения переменных, сохраненные в сессии.
  Integer accessedCount = (Integer)session.getAttribute("page.welcome.accessedCount");
  Instant accessedDate = (Instant)session.getAttribute("page.welcome.accessedDate");
%>

<html>
<head>
  <title>Title</title>
  <style>
    em {
      color: <%=userColor%>;
    }
  </style>
</head>
<body>
<h3>
  Hello, <em><%=userName%></em>!
</h3>
<br>
<h3>
  This page has been visited <em><%=accessedCount%></em> time(s). Last on
  <em><%=accessedDate%></em>
</h3>
</body>
</html>
