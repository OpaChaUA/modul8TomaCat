package modul8;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@WebServlet(value = "/time")
public class TimeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html; charset=UTF-8");

        String timezoneParam = req.getParameter("timezone");
        ZoneId zone = getZoneIdFromTimezoneParam(timezoneParam);
        String timezoneDisplay = getTimezoneDisplay(timezoneParam);

        LocalDateTime now = LocalDateTime.now(zone);
        String formattedDateTime = formatDateTime(now);

        String html = generateHtmlResponse(formattedDateTime, timezoneDisplay);
        resp.getWriter().write(html);
        resp.getWriter().close();
    }

    private ZoneId getZoneIdFromTimezoneParam(String timezoneParam) {
        if (timezoneParam != null && !timezoneParam.isEmpty()) {
            int offset = Integer.parseInt(timezoneParam.substring(4));
            return ZoneId.ofOffset("UTC", ZoneOffset.ofHours(offset));
        } else {
            return ZoneId.of("UTC");
        }
    }

    private String getTimezoneDisplay(String timezoneParam) {
        if (timezoneParam != null && !timezoneParam.isEmpty()) {
            int offset = Integer.parseInt(timezoneParam.substring(4));
            return "UTC+" + offset;
        } else {
            return "UTC";
        }
    }

    private String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }

    private String generateHtmlResponse(String formattedDateTime, String timezoneDisplay) {
        return "<html><body><h2>Поточний час (з точністю до секунд): " +
                formattedDateTime + " "
                + timezoneDisplay +
                "</h2></body></html>";
    }
}

