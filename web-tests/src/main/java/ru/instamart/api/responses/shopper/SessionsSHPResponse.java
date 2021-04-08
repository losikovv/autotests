package ru.instamart.api.responses.shopper;


import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.shopper.SessionDataSHP;
import ru.instamart.api.responses.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class SessionsSHPResponse extends BaseResponseObject {
    private SessionDataSHP data;
}
