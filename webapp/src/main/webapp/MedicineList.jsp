<%@ page import="services.MedicineService" %>
<%@ page import="models.Medicine" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Medicine List</title>
    <style>
        table, th, td {
            border: 1px solid black;
        }
    </style>
</head>
<body>
    <%! static final MedicineService medicineService = new MedicineService(); %>

    <%
        request.setCharacterEncoding("UTF-8");
        String maxPriceStr = request.getParameter("max-price");
        RequestDispatcher dispatcher;
        if (maxPriceStr == null) {
            dispatcher = request.getServletContext().getRequestDispatcher("/ErrorManager.jsp");
            dispatcher.forward(request, response);
            return;
        }
        int maxPrice = Integer.parseInt(maxPriceStr);
    %>

    <h1>List of medicines cheaper than <%=maxPrice%> rub/unit</h1>
    <table>
        <tr>
            <td><b>ID</b></td>
            <td><b>Name</b></td>
            <td><b>Quantity</b></td>
            <td><b>Unit Price</b></td>
        </tr>
        <% for (Medicine med : medicineService.findByMaxPrice(maxPrice)) {%>
            <tr>
                <% for (String field : med.fieldsToStringList()) { %>
                    <td><%=field%></td>
                <% } %>
            </tr>
        <% } %>
    </table>
</body>
</html>
