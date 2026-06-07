package controller;

import dao.UserDAO;
import model.User;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/AuthController")
public class AuthController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String contextPath = request.getContextPath();
        String action = request.getParameter("action");

        try {

            UserDAO dao = new UserDAO();

            // ================= LOGIN =================
            if ("login".equalsIgnoreCase(action)) {

                String email = request.getParameter("email");
                String password = request.getParameter("password");

                User u = dao.login(email, password);

                if (u == null) {
                    response.sendRedirect(contextPath + "/Login.jsp?error=invalid");
                    return;
                }

                HttpSession oldSession = request.getSession(false);
                if (oldSession != null) oldSession.invalidate();

                HttpSession session = request.getSession(true);
                session.setAttribute("user", u);
                session.setAttribute("role", u.getRole());

                String role = (u.getRole() != null) ? u.getRole().toLowerCase() : "";

                switch (role) {
                    case "lecturer":
                        response.sendRedirect(contextPath + "/lecturer-dashboard.jsp");
                        break;

                    case "admin":
                        response.sendRedirect(contextPath + "/course-list.jsp");
                        break;

                    case "student":
                        response.sendRedirect(contextPath + "/student-dashboard.jsp");
                        break;

                    default:
                        response.sendRedirect(contextPath + "/Login.jsp?error=role");
                        break;
                }

                return;
            }

            // ================= REGISTER =================
            if ("register".equalsIgnoreCase(action)) {

                User u = new User();

                u.setName(request.getParameter("name"));
                u.setEmail(request.getParameter("email"));
                u.setPassword(request.getParameter("password"));
                u.setRole(request.getParameter("role"));

                String matricNo = request.getParameter("matricNo");
                if (matricNo != null && !matricNo.isEmpty()) {
                    u.setMatricNo(matricNo);
                }

                dao.Register(u);

                response.sendRedirect(contextPath + "/Login.jsp?success=registered");
                return;
            }

            // ================= LOGOUT =================
            if ("logout".equalsIgnoreCase(action)) {

                HttpSession session = request.getSession(false);
                if (session != null) session.invalidate();

                response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
                response.setHeader("Pragma", "no-cache");
                response.setDateHeader("Expires", 0);

                response.sendRedirect(contextPath + "/index.jsp");
                return;
            }

            // DEFAULT
            response.sendRedirect(contextPath + "/Login.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/Login.jsp?error=system");
        }
    }

    // OPTIONAL GET LOGOUT
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session != null) session.invalidate();

        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }
}