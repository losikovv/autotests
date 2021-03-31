package instamart.api.objects.v2;

import instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SearchPhrase extends BaseObject {
    private String name;
    private Icon icon;
}
