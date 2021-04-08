package ru.instamart.api.objects.shopper;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class SessionAttributesSHP extends BaseObject {
    private String accessToken;
    private String refreshToken;
    private String expiresAt;
}
