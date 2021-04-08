package ru.instamart.api.objects.shopper;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ReasonSHP extends BaseObject {
    private Integer id;
    private String name;
    private Boolean active;
    private String created_at;
    private String updated_at;
    private Object position;
}
