package instamart.api.objects.delivery_club;

import instamart.api.objects.BaseObject;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDC extends BaseObject {
    private String name;
    private String phone;
}
