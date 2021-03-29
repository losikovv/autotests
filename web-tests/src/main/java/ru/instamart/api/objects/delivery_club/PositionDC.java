package instamart.api.objects.delivery_club;

import instamart.api.objects.BaseObject;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PositionDC extends BaseObject {
    private String id;
    private Integer quantity;
    private String price;
    private String discountPrice;
}
