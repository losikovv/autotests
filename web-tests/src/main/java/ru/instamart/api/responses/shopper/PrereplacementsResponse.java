package ru.instamart.api.responses.shopper;

import ru.instamart.api.objects.shopper.Prereplacement;
import ru.instamart.api.responses.BaseResponseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class PrereplacementsResponse extends BaseResponseObject {
    private Prereplacement prereplacement;
}
