package ru.instamart.api.responses.deliveryclub;

import ru.instamart.api.responses.BaseResponseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public final class TokenDCResponse extends BaseResponseObject {
    private String token;
    private String expiresAt;
}
