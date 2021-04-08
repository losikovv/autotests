package ru.instamart.api.responses.v1;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.v1.OfferV1;
import ru.instamart.api.objects.v2.MetaV2;
import ru.instamart.api.responses.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class OffersV1Response extends BaseResponseObject {
    private List<OfferV1> offers = null;
    private MetaV2 meta;
}
