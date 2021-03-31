package instamart.api.objects.delivery_club;

import instamart.api.objects.BaseObject;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Builder
public class CityDC extends BaseObject {
    private String name;
    private String code;
}
