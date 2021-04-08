package ru.instamart.api.responses.v1;

import ru.instamart.api.objects.v2.Meta;
import ru.instamart.api.objects.v1.Offer;
import ru.instamart.api.responses.BaseResponseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class OffersResponse extends BaseResponseObject {
    private List<Offer> offers = null;
    private Meta meta;
}
