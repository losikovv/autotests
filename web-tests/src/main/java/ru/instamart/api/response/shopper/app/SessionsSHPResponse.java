package ru.instamart.api.response.shopper.app;


import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.shopper.app.SessionDataSHP;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class SessionsSHPResponse extends BaseResponseObject {
    private SessionDataSHP data;
}
