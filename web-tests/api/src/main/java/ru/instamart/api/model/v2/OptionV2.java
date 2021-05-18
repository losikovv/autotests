package ru.instamart.api.model.v2;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class OptionV2 extends BaseObject {
    private Integer value;
    private Integer count;
    private Boolean active;
    private String name;
    private String permalink;
}
