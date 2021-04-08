package ru.instamart.api.objects.v2;

import ru.instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Errors extends BaseObject {
    private String email;
    private String password;
    private String fullname;
    private String shipments;
    private String base;
    private String payments;
}
