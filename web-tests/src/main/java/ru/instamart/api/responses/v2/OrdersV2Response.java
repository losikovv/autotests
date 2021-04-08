package ru.instamart.api.responses.v2;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.v2.MetaV2;
import ru.instamart.api.objects.v2.OrderV2;
import ru.instamart.api.responses.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class OrdersV2Response extends BaseResponseObject {
    private List<OrderV2> orders = null;
    private MetaV2 meta;
}
