package ru.instamart.api.response.shopper.app;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.shopper.app.PrereplacementSHP;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class PrereplacementsSHPResponse extends BaseResponseObject {
    private PrereplacementSHP prereplacement;
}
