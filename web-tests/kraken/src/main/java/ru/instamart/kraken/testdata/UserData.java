package ru.instamart.kraken.testdata;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.instamart.kraken.util.PhoneCrypt;

import static java.util.Objects.isNull;

@AllArgsConstructor
@Data
public final class UserData {

    private String role;
    private String login;
    private String phone;
    private String password;
    private String name;
    private String token;
    private String encryptedPhone;

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

    public UserData(String role, String login, String phone, String password, String name, String token) {
        this(role, login, phone, password, name, token, token);
        this.role = role;
        this.login = login;
        this.phone = phone;
        this.password = password;
        this.name = name;
        this.token = token;
        this.encryptedPhone = generateEncryptedPhone();
    }

    public String getFirstName() {
        if (name == null) return "FirstName";

        final String[] fullName = name.split(" ",2);

        return fullName.length >= 1 ? fullName[0] : "FirstName";
    }

    public String getLastName() {
        if (name == null) return "LastName";

        final String[] fullName = name.split(" ",2);

        return fullName.length > 1 ? fullName[1] : "LastName";
    }

    private String generateEncryptedPhone() {
        if (isNull(phone) || phone.isEmpty()) {
            return "phone_empty";
        }
        return PhoneCrypt.INSTANCE.encryptPhone(phone);
    }
}
