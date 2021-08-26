package ru.instamart.kraken.testdata;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import ru.instamart.kraken.util.PhoneCrypt;

import static java.util.Objects.isNull;

@AllArgsConstructor
@Data
public final class UserData {

    private String role;
    private String email;
    private String phone;
    private String password;
    private String name;
    private String token;
    private String encryptedPhone;
    private String anonymousId;

    public static UserDataBuilder builder() {
        return new UserDataBuilder();
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

    @ToString
    public static final class UserDataBuilder {
        private String role;
        private String email;
        private String phone;
        private String password;
        private String name;
        private String token;
        private String encryptedPhone;
        private String anonymousId;

        UserDataBuilder() {
        }

        public UserDataBuilder role(final String role) {
            this.role = role;
            return this;
        }

        public UserDataBuilder email(final String email) {
            this.email = email;
            return this;
        }

        public UserDataBuilder phone(final String phone) {
            this.phone = phone;
            return this;
        }

        public UserDataBuilder password(final String password) {
            this.password = password;
            return this;
        }

        public UserDataBuilder name(final String name) {
            this.name = name;
            return this;
        }

        public UserDataBuilder token(final String token) {
            this.token = token;
            return this;
        }

        public UserDataBuilder anonymousId(final String anonymousId) {
            this.anonymousId = anonymousId;
            return this;
        }

        public UserData build() {
            return new UserData(role, email, phone, password, name, token, generateEncryptedPhone(), anonymousId);
        }

        private String generateEncryptedPhone() {
            if (isNull(phone) || phone.isEmpty()) {
                return "phone_empty";
            }
            return PhoneCrypt.INSTANCE.encryptPhone(phone);
        }
    }
}
