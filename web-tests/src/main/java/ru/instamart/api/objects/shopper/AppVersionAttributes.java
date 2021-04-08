package ru.instamart.api.objects.shopper;

import ru.instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class AppVersionAttributes extends BaseObject {
    private Integer major;
    private String changelog;
    private String importance;
    private String downloadUrl;
}
