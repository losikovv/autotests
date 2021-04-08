package ru.instamart.api.responses.shopper;


import ru.instamart.api.responses.BaseResponseObject;
import ru.instamart.api.objects.shopper.SessionData;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SessionsResponse extends BaseResponseObject {
    private SessionData data;
}
