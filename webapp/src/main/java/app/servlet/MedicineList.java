package app.servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Лекарство в аптеке.
 */
class Medicine {
    public int id;
    public String name;
    public int quantity;
    public int unitPrice;

    /**
     * Конкструктор.
     * @param id ID
     * @param name Имя
     * @param quantity Количество на складе
     * @param unitPrice Цена единицы
     */
    public Medicine(int id, String name, int quantity, int unitPrice) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    /**
     * Возвращает список строковых значений полей класса.
     * @return Список строк
     */
    public List<String> fieldsToStringList() {
        return List.of(
            String.valueOf(this.id),
            this.name,
            String.valueOf(this.quantity),
            String.valueOf(this.unitPrice)
        );
    }
}

/**
 * Сервлет, отображающий список лекарсв.
 */
@WebServlet(name = "MedicineList", value = "/MedicineList")
public class MedicineList extends HttpServlet {
    /**
     * Список лекарств.
     */
    private final List<Medicine> medicines;

    /**
     * Конкструктор.
     */
    public MedicineList() {
        super();
        medicines = new ArrayList<>();
        medicines.add(new Medicine(0, "AAA", 10, 100));
        medicines.add(new Medicine(1, "BBB", 32, 200));
        medicines.add(new Medicine(2, "CCC", 1, 220));
        medicines.add(new Medicine(3, "DDD", 56, 1340));
        medicines.add(new Medicine(4, "EEE", 23, 50));
    }

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
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=UTF-8");

        var max_price_str = request.getParameter("max-price");
        var max_price = (max_price_str == null)
                                ? 0
                                : Integer.parseInt(max_price_str);
        var table_content = buildTableHtml(max_price);

        try (PrintWriter out = response.getWriter()) {
            out.println(
                "<html>"
                + "<head><title>Список лекарств</title></head>"
                + "<body>"
                        + "<h1>"
                            + "Список лекарств дешевле "
                            + max_price + " руб/шт"
                        + "</h1>"
                        + "<table border='1'>"
                        + "<tr>"
                            + "<td><b>ID</b></td>"
                            + "<td><b>Name</b></td>"
                            + "<td><b>Quantity</b></td>"
                            + "<td><b>Unit Price</b></td>"
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
     * @param max_price Максимальная цена (не включая)
     * @return HTML-содержимое таблицы
     */
    private String buildTableHtml(int max_price) {
        StringBuilder sb = new StringBuilder();
        for (var med : this.medicines) {
            if (med.unitPrice >= max_price) {
                continue;
            }
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