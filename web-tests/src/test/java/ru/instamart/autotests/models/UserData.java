package ru.instamart.autotests.models;

public class UserData {
    private final String login;
    private final String password;
    private final String name;

    public UserData(String login, String password, String name) {
        this.login = login;
        this.password = password;
        this.name = name;
    }

    public UserData(String login, String password) {
        this.login = login;
        this.password = password;
        this.name = null;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getName() { return name; }
}
