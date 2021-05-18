package ru.instamart.api.model.shopper.app;

import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@lombok.Data
@EqualsAndHashCode(callSuper=false)
public class DataSHP extends BaseObject {
    private String id;
    private String type;
    private AttributesSHP attributes;
}
