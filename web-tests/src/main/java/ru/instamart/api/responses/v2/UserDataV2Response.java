package ru.instamart.api.responses.v2;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.v2.UserV2;

@Data
@EqualsAndHashCode(callSuper=false)
public final class UserDataV2Response {
    private UserV2 user;
}
