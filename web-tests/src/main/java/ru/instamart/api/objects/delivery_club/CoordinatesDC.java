package instamart.api.objects.delivery_club;

import instamart.api.objects.BaseObject;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Builder
public class CoordinatesDC extends BaseObject {
    private String latitude;
    private String longitude;
}
