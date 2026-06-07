package controller;

import dao.CourseDAO;
import db.DBConnection;

import java.io.IOException;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/JoinCourseServlet")
public class JoinCourseServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String contextPath = request.getContextPath();
        Connection con = null;
        PreparedStatement check = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // ================= INPUT =================
            String studentId = request.getParameter("studentId");
            String courseCode = request.getParameter("courseCode");

            if (studentId == null || courseCode == null || studentId.trim().isEmpty() || courseCode.trim().isEmpty()) {
                response.sendRedirect(contextPath + "/student-dashboard.jsp?error=invalidInput");
                return;
            }

            CourseDAO dao = new CourseDAO();
            int courseId = dao.findCourseByCode(courseCode);

            // ================= COURSE NOT FOUND =================
            if (courseId == -1) {
                response.sendRedirect(contextPath + "/student-dashboard.jsp?error=courseNotFound");
                return;
            }

            con = DBConnection.getConnection();

            // ================= CHECK DUPLICATE =================
            check = con.prepareStatement("SELECT 1 FROM enrollment WHERE studentId=? AND courseId=?");
            check.setString(1, studentId);
            check.setInt(2, courseId);
            rs = check.executeQuery();

            if (rs.next()) {
                response.sendRedirect(contextPath + "/student-dashboard.jsp?error=alreadyJoined");
                return;
            }

            // ================= INSERT =================
            ps = con.prepareStatement("INSERT INTO enrollment(studentId, courseId) VALUES (?, ?)");
            ps.setString(1, studentId);
            ps.setInt(2, courseId);
            ps.executeUpdate();

            response.sendRedirect(contextPath + "/student-dashboard.jsp?success=joined");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(contextPath + "/student-dashboard.jsp?error=systemError");
        } finally {
            // Mencegah memory leak dengan menutup connection
            try {
                if (rs != null) rs.close();
                if (check != null) check.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}