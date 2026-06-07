package model;

public class User {

    private int userId;
    private String name;
    private String email;
    private String password;
    private String role;
    // Tambah pembolehubah ini supaya ia boleh disimpan dalam objek
    private String matricNo; 

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    // Sekarang method ini akan berfungsi dengan betul
    public String getMatricNo() {
        return matricNo;
    }

    public void setMatricNo(String matricNo) {
        this.matricNo = matricNo;
    }
}