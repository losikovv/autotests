package ru.instamart.api.model.shopper.admin;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class CustomSettingsItemV1 extends BaseObject {
    private String key;
    private String value;
}