package ru.instamart.api.responses.v2;

import ru.instamart.api.objects.v2.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public final class UserDataResponse {
    private User user;
}
