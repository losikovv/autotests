package instamart.api.objects.shopper;

import instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ReplacementAttributes extends BaseObject {
    private Integer fromItemId;
    private Integer toItemId;
    private String reason;
}
