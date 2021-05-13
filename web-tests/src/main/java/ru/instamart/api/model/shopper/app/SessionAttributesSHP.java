package ru.instamart.api.model.shopper.app;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class SessionAttributesSHP extends BaseObject {
    private String accessToken;
    private String refreshToken;
    private String expiresAt;
}
