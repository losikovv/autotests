package ru.instamart.api.objects.v2;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ErrorsV2 extends BaseObject {
    private String email;
    private String password;
    private String fullname;
    private String shipments;
    private String base;
    private String payments;
}
