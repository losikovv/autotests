package ru.instamart.kraken.data.chatwoot.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@AllArgsConstructor
@Data
public final class UserData {

    private String email;
    private String password;

    public static UserDataBuilder builder() {
        return new UserDataBuilder();
    }

    @ToString
    public static final class UserDataBuilder {
        private String email;
        private String password;

        UserDataBuilder() {
        }

        public UserDataBuilder email(final String email) {
            this.email = email;
            return this;
        }

        public UserDataBuilder password(final String password) {
            this.password = password;
            return this;
        }

        public UserData build() {
            return new UserData(email, password);
        }
    }

}
