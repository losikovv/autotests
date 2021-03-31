package instamart.api.objects.v2;

import instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ReplacementPolicy extends BaseObject {
    private Integer id;
    private String description;
}
