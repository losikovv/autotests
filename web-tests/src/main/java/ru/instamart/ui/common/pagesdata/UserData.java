package instamart.ui.common.pagesdata;

public final class UserData {

    private String role;
    private String login;
    private String phone;
    private String password;
    private String name;
    private String token;

    public UserData(final String role, final String login, final String phone, final String password, final String name, final String token) {
        this.role = role;
        this.login = login;
        this.phone = phone;
        this.password = password;
        this.name = name;
        this.token = token;
    }

    public UserData( String role, String login, String phone, String password, String name) {
        this(role, login, phone, password, name, null);
    }

    public UserData( String role, String login, String password, String name) {
        this(role, login, null, password, name);
    }

    public UserData(String login, String password, String name) {
        this(null, login, password, name);
    }

    public UserData(final String login, final String password) {
        this(login, password, null);
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
