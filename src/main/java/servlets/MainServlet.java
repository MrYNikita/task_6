package servlets;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@WebServlet(urlPatterns = { "/ppp" })
public class MainServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String requestURI = req.getRequestURI();

        requestURI = requestURI
                .substring(requestURI.indexOf("=") + 1)
                .replace("%20"," ");

        resp.getWriter().println("* Date: " + new Date().toString());
        resp.getWriter().println("* Path: " + requestURI);

        String[] fileNameCatalog = Methods.getFilePoints(requestURI);

        for (String point : fileNameCatalog) {
            resp.getWriter().println(point);
        }
    }

    @Override
    public void destroy() {
        destroy();
    }
}