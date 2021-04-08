package ru.instamart.api.objects.shopper;

import ru.instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class OperationShift extends BaseObject {
    private Integer id;
    private Integer shopperId;
    private Integer roleId;
    private String planStartsAt;
    private String factStartsAt;
    private String planEndsAt;
    private Object factEndsAt;
    private String state;
    private Role role;
}
