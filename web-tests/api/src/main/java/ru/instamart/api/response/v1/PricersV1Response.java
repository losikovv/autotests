package ru.instamart.api.response.v1;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.request.v1.ShippingMethodsV1Request.CalculatorType;
import ru.instamart.api.request.v1.ShippingMethodsV1Request.Preferences;
import ru.instamart.api.request.v1.ShippingMethodsV1Request.RulesType;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public final class PricersV1Response extends BaseResponseObject {

    private List<Pricer> pricers;

    @Data
    public static final class Pricer {
        private int id;
        private int position;
        private Calculator calculator;
        private List<Rules> rules;
    }

    @Data
    public static final class Calculator {
        private int id;
        private Preferences preferences;
        private CalculatorType type;
    }

    @Data
    public static final class Rules {
        private int id;
        private Preferences preferences;
        private RulesType type;
    }
}
