package instamart.api.objects.v2;

import instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Sort extends BaseObject {
    private String key;
    private String name;
    private String order;
    private Boolean active;
}
