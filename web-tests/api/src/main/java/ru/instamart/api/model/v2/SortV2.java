package ru.instamart.api.model.v2;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class SortV2 extends BaseObject {
    private String key;
    private String name;
    private String order;
    private Boolean active;
}
