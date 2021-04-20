package ru.instamart.api.responses.shopper.admin;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.shopper.admin.MetaV1;
import ru.instamart.api.objects.shopper.admin.TariffV1;
import ru.instamart.api.responses.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class TariffsSHPResponse extends BaseResponseObject {
    private List<TariffV1> tariffs = null;
    private MetaV1 meta;
}
