package servlets;

import accounts.AccountService;
import accounts.UserProfile;
import com.google.gson.Gson;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/sign")
public class ServletSign extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String sessionId = req.getSession().getId();
        UserProfile profile = AccountService.getUserBySessionId(sessionId);

        if (profile == null) {

            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        } else {

            Gson gson = new Gson();
            String json = gson.toJson(profile);
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().println(json);
            resp.setStatus(HttpServletResponse.SC_OK);
            req.setAttribute("path","D:\\Y_Projects\\Y_Java\\task_3\\filesUsers\\" + profile.getLogin());
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/filePointerJsp.jsp" + "?path=D:\\Y_Projects\\Y_Java\\task_3\\filesUsers\\" + profile.getLogin());
            System.out.println("WW");
            requestDispatcher.forward(req, resp);
            System.out.println(requestDispatcher);

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if (login == null || password == null) {

            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;

        }

        UserProfile profile = AccountService.getUserByLogin(login);

        if (profile == null || !profile.getPassword().equals(password)) {

            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;

        }

        AccountService.addSession(req.getSession().getId(),profile);
        Gson gson = new Gson();
        String json = gson.toJson(profile);
        resp.setContentType("text/html;charset=utf-8");
        req.setAttribute("path","D:\\Y_Projects\\Y_Java\\task_3\\filesUsers\\" + login);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/filePointerJsp.jsp" + "?path=D:\\Y_Projects\\Y_Java\\task_3\\filesUsers\\" + profile.getLogin());
        requestDispatcher.forward(req,resp);
        resp.setStatus(HttpServletResponse.SC_OK);

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
