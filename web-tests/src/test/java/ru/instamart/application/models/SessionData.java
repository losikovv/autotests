package ru.instamart.application.models;

public class SessionData {
    public String id;
    public UserData admin;
    public UserData user;
    public String orderList;
    public String userList;

    public SessionData(String id, UserData admin, UserData user, String orderList, String userList) {
        this.id = id;
        this.admin = admin;
        this.user = user;
        this.orderList = orderList;
        this.userList = userList;
    }
}
