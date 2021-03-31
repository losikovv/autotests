package instamart.api.objects.v2;

import instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Property extends BaseObject {
    private String name;
    private String presentation;
    private String value;
}
