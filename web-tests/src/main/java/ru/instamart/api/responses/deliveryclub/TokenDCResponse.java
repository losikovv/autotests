package instamart.api.responses.deliveryclub;

import instamart.api.responses.BaseResponseObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenDCResponse extends BaseResponseObject {
    private String token;
    private String expiresAt;
}
