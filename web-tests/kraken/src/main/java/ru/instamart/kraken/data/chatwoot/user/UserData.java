package ru.instamart.kraken.data.chatwoot.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class UserData {

    private String email;
    private String password;
}
