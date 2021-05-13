package ru.instamart.api.responses.shopper.admin;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.shopper.admin.TariffV1;
import ru.instamart.api.responses.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class TariffSHPResponse extends BaseResponseObject {
    private TariffV1 tariff;
}
