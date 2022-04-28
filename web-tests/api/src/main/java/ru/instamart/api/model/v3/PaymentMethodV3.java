package ru.instamart.api.model.v3;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class PaymentMethodV3 extends BaseObject {
    private String type;
    private String title;
    private List<PaymentMethodOptionV3> options = null;
}