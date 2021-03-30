package instamart.api.responses.deliveryclub;

import instamart.api.responses.BaseResponseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public final class TokenDCResponse extends BaseResponseObject {

    private String token;
    private String expiresAt;
}
