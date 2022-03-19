package app.servlet;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Optional;

/**
 * Сервлет, обрабатывающий попытку входа пользователя.
 */
@WebServlet(name = "LoginProcessor", value = "/LoginProcessor")
public class LoginProcessor extends HttpServlet {
    /**
     * Обрабатывает GET-запрос.
     * @param request Объект запроса
     * @param response Объект ответа
     * @throws IOException Ошибка ввода/вывода
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        // Получаем HTTP-сессию.
        var session = request.getSession(true);

        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("utf-8");

        // Читаем параметры, переданные из формы.
        var userName = readParameter(request, response, "userName");
        var userColor = readParameter(request, response, "userColor");

        if (userName == null || userColor == null) {
            return;
        }

        // Создаем куки с именем пользователя и его цветом.
        createCookie(response, "user.name", userName);
        createCookie(response, "user.color", userColor);
        // Обновляем переменные сессии:
        //     - Инкрементируем число посещений.
        //     - Обновляем дату последнего входа.
        updateOrCreateSessionAttribute(session, "page.welcome.accessedCount",
                x -> x.map(y -> ((Integer)y) + 1).orElse(1));
        updateOrCreateSessionAttribute(session, "page.welcome.accessedDate",
                x -> Instant.now());

        // Производим редирект на целевую страницу.
        var redirectPage = request.getContextPath() + "/Welcome.jsp";
        response.sendRedirect(response.encodeRedirectURL(redirectPage));
    }

    /**
     * Создает новое куки.
     * @param response Объект ответа.
     * @param name Имя куки.
     * @param value Значение куки.
     */
    private void createCookie(HttpServletResponse response,
                              String name, String value) {
        var c = new Cookie(name, URLEncoder.encode(value, StandardCharsets.UTF_8));
        c.setMaxAge(60 * 60 * 24);
        response.addCookie(c);
    }

    /**
     * Представляет метод, обновляющий значение переменной HTTP-сессии.
     */
    interface HttpSessionAttributeUpdater {
        Object update(Optional<Object> oldValue);
    }

    /**
     * Обновляет переменную HTTP-сессии.
     * @param session Объект сессии.
     * @param name Имя переменной.
     * @param updater Объект-обновитель.
     */
    private void updateOrCreateSessionAttribute(HttpSession session, String name, HttpSessionAttributeUpdater updater) {
        var oldValue = session.getAttribute(name);
        var newValue = updater.update(Optional.ofNullable(oldValue));
        session.setAttribute(name, newValue);
    }

    /**
     * Читает параметр GET-запроса.
     *
     * При отсутствии параметра устанавливает HTTP-код и сообщение.
     * При отсутствии параметра устанавливает HTTP-код и сообщение.
     *
     * @param request Объект запроса.
     * @param response Объект ответа.
     * @param name Имя параметра.
     * @return Значение параметра.
     * @throws IOException Ошибка ввода/вывода.
     */
    private String readParameter(HttpServletRequest request, HttpServletResponse response, String name)
            throws IOException {
        var parameter = request.getParameter(name);
        if(parameter == null) {
            var error = "Parameter \"" + name + "\" is not set";
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, error);
        }
        return parameter;
    }
}