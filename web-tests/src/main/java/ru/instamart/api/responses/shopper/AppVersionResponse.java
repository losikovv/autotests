package ru.instamart.api.responses.shopper;

import ru.instamart.api.objects.shopper.AppVersionData;
import ru.instamart.api.responses.BaseResponseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class AppVersionResponse extends BaseResponseObject {
    private AppVersionData data;
}
