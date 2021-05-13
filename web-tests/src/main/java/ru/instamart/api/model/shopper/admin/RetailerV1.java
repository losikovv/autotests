package ru.instamart.api.model.shopper.admin;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class RetailerV1 extends BaseObject {
    private Integer id;
    private String name;
    private String slug;
    private Integer position;
    private Boolean available;
    private String logoUrl;
    private String logoBackgroundColor;
}
