package instamart.api.responses.v2;

import instamart.api.responses.BaseResponseObject;
import instamart.api.objects.v2.ShippingRate;

import java.util.List;

public class ShippingRatesResponse extends BaseResponseObject {

    private List<ShippingRate> shipping_rates = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public ShippingRatesResponse() {
    }

    /**
     *
     * @param shipping_rates
     */
    public ShippingRatesResponse(List<ShippingRate> shipping_rates) {
        super();
        this.shipping_rates = shipping_rates;
    }

    public List<ShippingRate> getShipping_rates() {
        return shipping_rates;
    }

    public void setShipping_rates(List<ShippingRate> shipping_rates) {
        this.shipping_rates = shipping_rates;
    }

}
