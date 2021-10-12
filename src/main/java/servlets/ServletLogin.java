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

@WebServlet(urlPatterns = { "/registration" })
public class ServletLogin extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("WW");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String login = req.getParameter("login");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (login == null || password == null || email == null) {

            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        }

        AccountService.addNewUser(new UserProfile(login,email,password));

        UserProfile up = AccountService.getUserByLogin(login);

        AccountService.addSession(req.getSession().getId(),up);
        Gson gson = new Gson();
        String json = gson.toJson(up);
        resp.setContentType("text/html;charset=utf-8");
        req.setAttribute("path","D:\\Y_Projects\\Y_Java\\task_3\\filesUsers\\" + login);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/filePointerJsp.jsp" + "?path=D:\\Y_Projects\\Y_Java\\task_3\\filesUsers\\" + up.getLogin());
        requestDispatcher.forward(req,resp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
