package app.servlet;

import models.Medicine;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import services.MedicineService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Сервлет, отображающий список лекарсв.
 */
//@WebServlet(name = "MedicineList", value = "/MedicineList")
public class MedicineList extends HttpServlet {
    private final MedicineService medicineService = new MedicineService();

    /**
     * Выполняет GET и POST HTTP-запросы.
     *
     * @param request Запрос к сервлету
     * @param response Ответ сервлета
     * @throws ServletException Внутренняя ошибка
     * @throws IOException Ошибка ввода вывода
     */
    protected void processRequest(HttpServletRequest request,
                                  HttpServletResponse response)
            throws ServletException, IOException {

        var lang = request.getParameter("lang");
        if (lang == null) {
            response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE,
                    "Ожидался параметр lang");
            return;
        }
        if (!"en".equalsIgnoreCase(lang) && !"ru".equalsIgnoreCase(lang)) {
            response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE,
                    "Параметр lang может принимать значения en или ru");
            return;
        }

        var res = ResourceBundle.getBundle(
                                    "/MedicineList",
                                    new Locale(lang));
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=UTF-8");

        var max_price_str = request.getParameter("max-price");
        var max_price = (max_price_str == null)
                                ? 0
                                : Integer.parseInt(max_price_str);
        var table_content = buildTableHtml(max_price);

        var head_title = res.getString("head.title");
        var body_title = res.getString("body.title");
        var price_units = res.getString("price_units");
        var id = res.getString("table.id");
        var name = res.getString("table.name");
        var quantity = res.getString("table.quantity");
        var unit_price = res.getString("table.unit_price");

        try (PrintWriter out = response.getWriter()) {
            out.println(
                "<html>"
                + "<head><title>" + head_title + "</title></head>"
                + "<body>"
                        + "<h1>"
                            + body_title + " "
                            + max_price + " " + price_units
                        + "</h1>"
                        + "<table border='1'>"
                        + "<tr>"
                            + "<td><b>" + id + "</b></td>"
                            + "<td><b>" + name + "</b></td>"
                            + "<td><b>" + quantity + "</b></td>"
                            + "<td><b>" + unit_price + "</b></td>"
                        + "</tr>"
                        + table_content
                        + "</table>"
                + "</body" +
                "</html>"
            );
        }
    }

    /**
     * Создает содержимое таблицы для всех лекарств, стоимость единицы
     * которых меньше, чем `max_price`.
     * @param maxPrice Максимальная цена (не включая)
     * @return HTML-содержимое таблицы
     */
    private String buildTableHtml(int maxPrice) {
        StringBuilder sb = new StringBuilder();
        for (var med : this.medicineService.findByMaxPrice(maxPrice)) {
            sb.append("<tr>");
            for (String field : med.fieldsToStringList()) {
                sb.append("<td>");
                sb.append(field);
                sb.append("</td>");
            }
            sb.append("</tr>");
        }
        return sb.toString();
    }

    /**
     * Выполняет GET HTTP-запрос.
     *
     * @param request Запрос к сервлету
     * @param response Ответ сервлета
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Выполняет POST HTTP-запрос.
     *
     * @param request Запрос к сервлету
     * @param response Ответ сервлета
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}