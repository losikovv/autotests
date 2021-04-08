package ru.instamart.api.objects.v2;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class FacetV2 extends BaseObject {
    private String key;
    private String name;
    private String type;
    private List<OptionV2> options = null;
}
