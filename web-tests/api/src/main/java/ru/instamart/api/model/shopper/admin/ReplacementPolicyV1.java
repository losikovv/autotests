package ru.instamart.api.model.shopper.admin;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ReplacementPolicyV1 extends BaseObject {
    private String type;
    private String name;
    private String description;
}
