package dao;

import db.DBConnection;
import model.Course;
import java.sql.*;
import java.util.*;

public class CourseDAO {

    // BUANG: Connection con = DBConnection.getConnection(); (Jangan simpan global!)

    // ================= CREATE =================
    public void addCourse(Course c) throws Exception {
        String sql = "INSERT INTO courses(title, description, courseCode, lecturerId) VALUES(?,?,?,?)";
        
        try (Connection con = DBConnection.getConnection(); 
             PreparedStatement ps = con.prepareStatement(sql)) {
             
            ps.setString(1, c.getTitle());
            ps.setString(2, c.getDescription());
            ps.setString(3, c.getCourseCode());
            ps.setInt(4, c.getLecturerId());
            ps.executeUpdate();
        }
    }

    // ================= UPDATE =================
    public void updateCourse(Course c) throws Exception {
        String sql;
        if (c.getLecturerId() > 0) {
            sql = "UPDATE courses SET title=?, description=?, courseCode=? WHERE courseId=? AND lecturerId=?";
        } else {
            sql = "UPDATE courses SET title=?, description=?, courseCode=? WHERE courseId=?";
        }

        try (Connection con = DBConnection.getConnection(); 
             PreparedStatement ps = con.prepareStatement(sql)) {
             
            ps.setString(1, c.getTitle());
            ps.setString(2, c.getDescription());
            ps.setString(3, c.getCourseCode());
            ps.setInt(4, c.getCourseId());
            if (c.getLecturerId() > 0) {
                ps.setInt(5, c.getLecturerId());
            }
            ps.executeUpdate();
        }
    }

    // ================= DELETE =================
    public void deleteCourse(int courseId, int lecturerId) throws Exception {
        String sql = "DELETE FROM courses WHERE courseId=? AND lecturerId=?";
        try (Connection con = DBConnection.getConnection(); 
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, courseId);
            ps.setInt(2, lecturerId);
            ps.executeUpdate();
        }
    }

    public void deleteCourseByAdmin(int courseId) throws Exception {
        String sql = "DELETE FROM courses WHERE courseId=?";
        try (Connection con = DBConnection.getConnection(); 
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, courseId);
            ps.executeUpdate();
        }
    }

    // ================= GET ALL =================
    public List<Course> getAllCourses() throws Exception {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT * FROM courses";
        
        try (Connection con = DBConnection.getConnection(); 
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
             
            while (rs.next()) {
                Course c = new Course();
                c.setCourseId(rs.getInt("courseId"));
                c.setTitle(rs.getString("title"));
                c.setDescription(rs.getString("description"));
                c.setCourseCode(rs.getString("courseCode"));
                c.setLecturerId(rs.getInt("lecturerId"));
                list.add(c);
            }
        }
        return list;
    }

    // ================= GET BY ID =================
    public Course getCourseById(int courseId) throws Exception {
        String sql = "SELECT * FROM courses WHERE courseId=?";
        try (Connection con = DBConnection.getConnection(); 
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, courseId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Course c = new Course();
                    c.setCourseId(rs.getInt("courseId"));
                    c.setTitle(rs.getString("title"));
                    c.setDescription(rs.getString("description"));
                    c.setCourseCode(rs.getString("courseCode"));
                    c.setLecturerId(rs.getInt("lecturerId"));
                    return c;
                }
            }
        }
        return null;
    }
// ================= FIND COURSE BY CODE =================
    public int findCourseByCode(String code) throws Exception {
        String sql = "SELECT courseId FROM courses WHERE courseCode=?";
        
        try (Connection con = DBConnection.getConnection(); 
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, code);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("courseId");
                }
            }
        }
        return -1; // Jika tiada kursus dijumpai
    }
    // ================= GET COURSES BY STUDENT =================
public List<Course> getCoursesByStudent(int studentId) throws Exception {

    List<Course> list = new ArrayList<>();

    String sql =
        "SELECT c.* " +
        "FROM courses c " +
        "INNER JOIN enrollment e ON c.courseId = e.courseId " +
        "WHERE e.studentId = ?";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, studentId);

        try (ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Course c = new Course();

                c.setCourseId(rs.getInt("courseId"));
                c.setTitle(rs.getString("title"));
                c.setDescription(rs.getString("description"));
                c.setCourseCode(rs.getString("courseCode"));
                c.setLecturerId(rs.getInt("lecturerId"));

                list.add(c);
            }
        }
    }

    return list;
}
}