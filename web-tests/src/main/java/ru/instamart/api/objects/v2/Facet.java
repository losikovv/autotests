package ru.instamart.api.objects.v2;

import ru.instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class Facet extends BaseObject {
    private String key;
    private String name;
    private String type;
    private List<Option> options = null;
}
