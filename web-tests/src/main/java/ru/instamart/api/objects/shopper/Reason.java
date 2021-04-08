package ru.instamart.api.objects.shopper;

import ru.instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Reason extends BaseObject {
    private Integer id;
    private String name;
    private Boolean active;
    private String created_at;
    private String updated_at;
    private Object position;
}
