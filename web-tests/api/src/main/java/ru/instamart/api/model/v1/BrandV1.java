package ru.instamart.api.model.v1;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper = false)
public class BrandV1 extends BaseObject {
    private Long id;
    private String name;
}
