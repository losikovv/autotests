package ru.instamart.api.responses.shopper.app;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.shopper.app.ReplacementDataSHP;
import ru.instamart.api.responses.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ReplacementSHPResponse extends BaseResponseObject {
    private ReplacementDataSHP data;
}
