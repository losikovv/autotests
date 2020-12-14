package instamart.ui.common.pagesdata;

public class UserData {
    private String role;
    private String login;
    private String phone;
    private String password;
    private String name;
    private String token;

    public UserData( String role, String email, String phone, String password, String name, String token) {
        this.role = role;
        this.login = email;
        this.phone = phone;
        this.password = password;
        this.name = name;
        this.token = token;
    }

    public UserData( String role, String email, String phone, String password, String name) {
        this.role = role;
        this.login = email;
        this.phone = phone;
        this.password = password;
        this.name = name;
    }

    public UserData( String role, String email, String password, String name) {
        this.role = role;
        this.login = email;
        this.password = password;
        this.name = name;
    }

    public UserData(String email, String password, String name) {
        this.login = email;
        this.password = password;
        this.name = name;
    }

    //public UserData(String role, String login, String password) { this.login = login;this.password = password;this.role = role; }

    public UserData(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getRole() { return role; }

    public String getLogin() { return login; }

    public String getPhone() { return phone; }

    public String getPassword() { return password; }

    public String getName() { return name; }

    public void setRole(String role) { this.role = role; }

    public void setLogin(String login) { this.login = login; }

    public void setPhone(String phone) { this.phone = phone; }

    public void setPassword(String password) { this.password = password; }

    public void setName(String name) { this.name = name; }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
