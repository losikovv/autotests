package ru.instamart.api.response.v3;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v3.PaymentMethodV3;
import ru.instamart.api.model.v3.ReplacementOptionV3;
import ru.instamart.api.model.v3.ShippingMethodV3;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class OrderOptionsV3Response extends BaseResponseObject {
    private List<Object> optionals = null;
    private List<PaymentMethodV3> payment_methods = null;
    private List<ShippingMethodV3> shipping_methods = null;
    private List<ReplacementOptionV3> replacement_options = null;
}
