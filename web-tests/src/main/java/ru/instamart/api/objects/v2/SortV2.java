package ru.instamart.api.objects.v2;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class SortV2 extends BaseObject {
    private String key;
    private String name;
    private String order;
    private Boolean active;
}
