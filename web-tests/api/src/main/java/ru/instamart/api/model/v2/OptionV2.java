package ru.instamart.api.model.v2;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper=false)
public class OptionV2 extends BaseObject {
    private Integer value;
    private Integer count;
    private Boolean active;
    private String name;
    @Null
    private String permalink;
}
