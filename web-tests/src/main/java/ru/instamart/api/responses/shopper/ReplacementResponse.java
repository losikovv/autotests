package ru.instamart.api.responses.shopper;

import ru.instamart.api.objects.shopper.ReplacementData;
import ru.instamart.api.responses.BaseResponseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ReplacementResponse extends BaseResponseObject {
    private ReplacementData data;
}
