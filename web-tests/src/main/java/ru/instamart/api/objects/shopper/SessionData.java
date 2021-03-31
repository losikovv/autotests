package instamart.api.objects.shopper;

import instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SessionData extends BaseObject {
    private String id;
    private String type;
    private SessionAttributes attributes;
}
