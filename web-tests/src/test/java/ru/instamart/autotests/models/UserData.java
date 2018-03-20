package ru.instamart.autotests.models;

// модель данных юзера
public class UserData {
    private final String login;
    private final String password;
    private final String name;

    public UserData(String login, String password, String name) {
        this.login = login;
        this.password = password;
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getName() { return name;

    }
}
