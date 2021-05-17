package ru.instamart.api.response.delivery_club;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public final class TokenDCResponse extends BaseResponseObject {
    private String token;
    private String expiresAt;
}
