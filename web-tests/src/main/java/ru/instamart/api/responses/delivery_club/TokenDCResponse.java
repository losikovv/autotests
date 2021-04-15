package ru.instamart.api.responses.delivery_club;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.responses.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public final class TokenDCResponse extends BaseResponseObject {
    private String token;
    private String expiresAt;
}
