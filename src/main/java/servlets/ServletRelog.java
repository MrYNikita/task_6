package servlets;

import accounts.AccountService;
import accounts.UserProfile;
import com.google.gson.Gson;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.RequestDispatcher;
import java.io.IOException;

@WebServlet("/relog")
public class ServletRelog extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = req.getSession().getId();
        UserProfile profile = AccountService.getUserBySessionId(sessionId);

        if (profile == null) {

            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED);

        } else {

            AccountService.deleteSession(sessionId);
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(javax.servlet.http.HttpServletResponse.SC_OK);
            resp.sendRedirect("/html/html_enter.html");

        }
    }
}
