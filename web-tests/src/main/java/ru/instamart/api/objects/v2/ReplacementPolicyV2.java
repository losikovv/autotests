package ru.instamart.api.objects.v2;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ReplacementPolicyV2 extends BaseObject {
    private Integer id;
    private String description;
}
