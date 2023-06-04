package modul8;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZoneOffset;


@WebFilter(urlPatterns = "/time")
public class TimezoneValidateFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String timezoneParam = request.getParameter("timezone");
        if (!isValidTimezone(timezoneParam)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid timezone");
            response.getWriter().close();
            return;
        }

        filterChain.doFilter(request, response);
    }

    public void destroy() {
    }

    private boolean isValidTimezone(String timezoneParam) {
        if (timezoneParam != null && !timezoneParam.isEmpty()) {
            try {
                int offset = Integer.parseInt(timezoneParam.substring(4));
                ZoneId.ofOffset("UTC", ZoneOffset.ofHours(offset));
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }
}


