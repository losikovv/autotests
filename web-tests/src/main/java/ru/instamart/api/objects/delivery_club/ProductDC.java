package instamart.api.objects.delivery_club;

import instamart.api.objects.BaseObject;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDC extends BaseObject {
    public String id;
    public String price;
    public String discountPrice;
    public String name;
    public List<String> categories;
    public Integer grammar;
    public Integer weight;
    public Integer volume;
    public Integer vat;
    public List<String> imageUrl;
    public String brand;
}
