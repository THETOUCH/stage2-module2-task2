package com.example.servlet;

import com.example.Users;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebFilter("/login")
public class LoginServlet extends HttpServlet {
    //write your code here!
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("user") == null ) {
            resp.sendRedirect("/login.jsp");
        } else {
            resp.sendRedirect("/user/hello.jsp");
        }
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String requestLogin = req.getParameter("login");
        String requestPassword = req.getParameter("password");
        List<String> users = Users.getInstance().getUsers();
        boolean isLoginRight = requestLogin != null && !requestLogin.isEmpty() && users.contains(requestLogin);
        boolean isPasswordRight = requestPassword != null && !requestPassword.isEmpty() && !requestPassword.trim().isEmpty();
        if (isLoginRight && isPasswordRight) {
            req.getRequestDispatcher("/user/hello.jsp").forward(req, resp);
            session.setAttribute("user", requestLogin);
        } else {
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}
