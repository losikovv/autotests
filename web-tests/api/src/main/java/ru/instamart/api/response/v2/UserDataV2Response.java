package ru.instamart.api.response.v2;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v2.UserV2;

@Data
@EqualsAndHashCode(callSuper=false)
public final class UserDataV2Response {
    private UserV2 user;
}
