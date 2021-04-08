package ru.instamart.api.objects.v1;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShippingCategoryV1 {
    private String name;
    private String slug;
}

