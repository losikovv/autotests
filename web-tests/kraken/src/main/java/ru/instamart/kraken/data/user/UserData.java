package ru.instamart.kraken.data.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import ru.instamart.kraken.config.CoreProperties;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.util.PhoneCrypt;
import ru.instamart.kraken.util.StringUtil;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@AllArgsConstructor
@Data
public final class UserData {

    private String id;
    private String role;
    private String email;
    private String phone;
    private String smsCode;
    private String password;
    private String name;
    private String token;
    private String encryptedPhone;
    private String anonymousId;
    private String qaSessionId;

    public static UserDataBuilder builder() {
        return new UserDataBuilder();
    }

    public String getFirstName() {
        if (isNull(name)) return "FirstName";

        final String[] fullName = name.split(" ", 2);

        return fullName.length >= 1 ? fullName[0] : "FirstName";
    }

    public String getLastName() {
        if (isNull(name)) return "LastName";

        final String[] fullName = name.split(" ", 2);

        return fullName.length > 1 ? fullName[1] : "LastName";
    }

    @ToString
    public static final class UserDataBuilder {
        private String id;
        private String role;
        private String email;
        private String phone;
        private String password;
        private String name;
        private String token;
        private String anonymousId;
        private String qaSessionId;

        UserDataBuilder() {
        }

        public UserDataBuilder id(final String id) {
            this.id = id;
            return this;
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

        public UserDataBuilder qaSessionId(final String qaSessionId) {
            this.qaSessionId = qaSessionId;
            return this;
        }

        public UserData build() {
            return new UserData(id, role, email, phone, getSmsCode(), password, name, token, generateEncryptedPhone(), anonymousId, qaSessionId);
        }

        /**
         * @return - id не null, только когда пользователь создается через qa ручку.
         * Такие пользователи требуют что бы sms code состоял из последних 6 цифр номера телефона,
         * во всех остальных случаях нужна {@link CoreProperties#DEFAULT_SMS}
         */
        private String getSmsCode() {
            if (nonNull(id) && EnvironmentProperties.SERVER.equalsIgnoreCase("preprod")) {
                return StringUtil.getSMSCode(phone);
            } else {
                return CoreProperties.DEFAULT_SMS;
            }
        }

        private String generateEncryptedPhone() {
            if (isNull(phone) || phone.isEmpty()) {
                return "phone_empty";
            }
            return PhoneCrypt.INSTANCE.encryptPhone(phone);
        }
    }
}
