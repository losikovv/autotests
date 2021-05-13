package ru.instamart.api.model.delivery_club;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ProductDC extends BaseObject {
    private String id;
    private String price;
    private String discountPrice;
    private String name;
    private List<String> categories;
    private Integer grammar;
    private Integer weight;
    private Integer volume;
    private Integer vat;
    private List<String> imageUrl;
    private String brand;
}
