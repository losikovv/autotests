package ru.instamart.api.responses.shopper;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.shopper.PrereplacementSHP;
import ru.instamart.api.responses.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class PrereplacementsSHPResponse extends BaseResponseObject {
    private PrereplacementSHP prereplacement;
}
