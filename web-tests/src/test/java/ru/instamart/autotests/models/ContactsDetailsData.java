package ru.instamart.autotests.models;

public class ContactsDetailsData {

    private String name;
    private String surname;
    private String email;
    private boolean newPhone;
    private String phone;
    private boolean sendEmail;

    public ContactsDetailsData(String name, String surname, String email, boolean newPhone, String phone, boolean sendEmail) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.newPhone = newPhone;
        this.phone = phone;
        this.sendEmail = sendEmail;
    }

    public ContactsDetailsData(boolean newPhone, String phone) {
        this.newPhone = newPhone;
        this.phone = phone;
    }

    public ContactsDetailsData(String phone) {
        this.phone = phone;
    }

    // Getters

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public boolean isNewPhone() {
        return newPhone;
    }

    public String getPhone() {
        return phone;
    }

    public boolean isSendEmail() {
        return sendEmail;
    }
}
