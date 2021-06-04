package ru.instamart.api.model.v2.ExternalPartnersV2;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@EqualsAndHashCode(callSuper = true)
@Data
public class CheckoutV2 extends BaseObject {
    private String text;
    private String promocode;
}
