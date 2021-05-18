package ru.instamart.api.response.shopper.admin;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.shopper.admin.MetaV1;
import ru.instamart.api.model.shopper.admin.TariffV1;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class TariffsSHPResponse extends BaseResponseObject {
    private List<TariffV1> tariffs = null;
    private MetaV1 meta;
}
