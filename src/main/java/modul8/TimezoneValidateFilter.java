package modul8;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZoneId;
import java.util.Set;

@WebFilter(value = "/time")
public class TimezoneValidateFilter extends HttpFilter {

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String timezoneParam = request.getParameter("timezone");
        if (timezoneParam == null || timezoneParam.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid timezone");
            response.getWriter().close();
        } else {
            chain.doFilter(req, resp);
        }
    }

}
