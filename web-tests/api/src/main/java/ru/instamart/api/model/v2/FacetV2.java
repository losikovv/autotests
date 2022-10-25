package ru.instamart.api.model.v2;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class FacetV2 extends BaseObject {
    private String key;
    @Null
    private String name;
    private String type;
    private List<OptionV2> options = null;
}
