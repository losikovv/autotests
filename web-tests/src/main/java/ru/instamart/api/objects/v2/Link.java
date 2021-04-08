package ru.instamart.api.objects.v2;

import ru.instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Link extends BaseObject {
    private String type;
    private String name;
    private String url;
}
