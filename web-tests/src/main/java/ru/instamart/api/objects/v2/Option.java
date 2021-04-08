package ru.instamart.api.objects.v2;

import ru.instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Option extends BaseObject {
    private Integer value;
    private Integer count;
    private Boolean active;
    private String name;
    private String permalink;
}
