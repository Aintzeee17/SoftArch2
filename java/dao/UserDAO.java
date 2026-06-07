package dao;

import db.DBConnection;
import model.User;
import java.sql.*;

public class UserDAO {

    public void Register(User u) throws Exception {
        String sql = "INSERT INTO users(name, email, password, role) VALUES (?,?,?,?)";
        
        // Try-with-resources memastikan connection & ps ditutup secara automatik
        try (Connection con = DBConnection.getConnection(); 
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, u.getName());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getPassword());
            ps.setString(4, u.getRole());
            
            ps.executeUpdate();
        }
    }

    public User login(String email, String password) throws Exception {
        String sql = "SELECT * FROM users WHERE email=? AND password=?";
        
        try (Connection con = DBConnection.getConnection(); 
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, email);
            ps.setString(2, password);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User u = new User();
                    u.setUserId(rs.getInt("userId"));
                    u.setName(rs.getString("name"));
                    u.setEmail(rs.getString("email"));
                    u.setRole(rs.getString("role"));
                    return u;
                }
            }
        }
        return null;
    }
    
}