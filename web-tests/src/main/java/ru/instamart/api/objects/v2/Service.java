package ru.instamart.api.objects.v2;

import ru.instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Service extends BaseObject {
    private Integer id;
    private String key;
    private String description;
}
