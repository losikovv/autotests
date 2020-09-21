package instamart.ui.common.pagesdata;

public class ContactsDetailsData {

    private boolean changeFirstName;
    private String firstName;
    private boolean changeLastName;
    private String lastName;
    private boolean changeEmail;
    private String email;
    private boolean addAnotherPhone;
    private String phone;
    private boolean sendEmail;

    public ContactsDetailsData(boolean changeFirstName, String firstName, boolean changeLastName, String lastName, boolean changeEmail, String email, boolean addAnotherPhone, String phone, boolean sendEmail) {
        this.changeFirstName = changeFirstName;
        this.firstName = firstName;
        this.changeLastName = changeLastName;
        this.lastName = lastName;
        this.changeEmail = changeEmail;
        this.email = email;
        this.addAnotherPhone = addAnotherPhone;
        this.phone = phone;
        this.sendEmail = sendEmail;
    }

    public ContactsDetailsData(String firstName, String lastName, String email, boolean addAnotherPhone, String phone, boolean sendEmail) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.addAnotherPhone = addAnotherPhone;
        this.phone = phone;
        this.sendEmail = sendEmail;
    }

    public ContactsDetailsData(boolean addAnotherPhone, String phone) {
        this.addAnotherPhone = addAnotherPhone;
        this.phone = phone;
    }

    public ContactsDetailsData(String phone) {
        this.phone = phone;
    }

    // Getters

    public boolean changeFirstName() {
        return changeFirstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public boolean changeLastName() {
        return changeLastName;
    }

    public String getLastName() {
        return lastName;
    }

    public boolean changeEmail() {
        return changeEmail;
    }

    public String getEmail() {
        return email;
    }

    public boolean addNewPhone() {
        return addAnotherPhone;
    }

    public String getPhone() {
        return phone;
    }

    public boolean sendEmails() {
        return sendEmail;
    }
}
