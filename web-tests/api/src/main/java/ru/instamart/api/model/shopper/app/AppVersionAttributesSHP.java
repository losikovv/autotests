package ru.instamart.api.model.shopper.app;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class AppVersionAttributesSHP extends BaseObject {
    private Integer major;
    private String changelog;
    private String importance;
    private String downloadUrl;
}
