package instamart.api.responses.shopper;


import instamart.api.responses.BaseResponseObject;
import instamart.api.objects.shopper.SessionData;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SessionsResponse extends BaseResponseObject {
    private SessionData data;
}
