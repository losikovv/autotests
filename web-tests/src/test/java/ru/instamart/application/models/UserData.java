package ru.instamart.application.models;

public class UserData {
    private String role;
    private String email;
    private String phone;
    private String password;
    private String name;

    public UserData( String role, String email, String phone, String password, String name) {
        this.role = role;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.name = name;
    }

    public UserData( String role, String email, String password, String name) {
        this.role = role;
        this.email = email;
        this.password = password;
        this.name = name;
    }

    //public UserData(String role, String email, String password) { this.email = email;this.password = password;this.role = role; }

    public UserData(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getRole() { return role; }

    public String getEmail() { return email; }

    public String getPhone() { return phone; }

    public String getPassword() { return password; }

    public String getName() { return name; }

    public void setRole(String role) { this.role = role; }

    public void setEmail(String email) { this.email = email; }

    public void setPhone(String phone) { this.phone = phone; }

    public void setPassword(String password) { this.password = password; }

    public void setName(String name) { this.name = name; }
}
