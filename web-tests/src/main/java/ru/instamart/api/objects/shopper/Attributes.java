package ru.instamart.api.objects.shopper;

import ru.instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Attributes extends BaseObject {
    private String accessToken;
    private String refreshToken;
    private String expiresAt;
}
