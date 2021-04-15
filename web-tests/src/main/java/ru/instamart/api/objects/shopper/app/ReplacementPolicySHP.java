package ru.instamart.api.objects.shopper.app;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ReplacementPolicySHP extends BaseObject {
    private String type;
    private String name;
    private String description;
}
