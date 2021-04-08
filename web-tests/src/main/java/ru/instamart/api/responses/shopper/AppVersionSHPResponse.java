package ru.instamart.api.responses.shopper;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.shopper.AppVersionDataSHP;
import ru.instamart.api.responses.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class AppVersionSHPResponse extends BaseResponseObject {
    private AppVersionDataSHP data;
}
