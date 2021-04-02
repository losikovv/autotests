package instamart.ui.common.pagesdata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public final class UserData {

    private String role;
    private String login;
    private String phone;
    private String password;
    private String name;
    private String token;

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

    public String getFirstName() {
        final String[] fullName = name.split(" ",2);

        return fullName.length >= 1 ? fullName[0] : "FirstName";
    }

    public String getLastName() {
        final String[] fullName = name.split(" ",2);

        return fullName.length > 1 ? fullName[1] : "LastName";
    }
}
