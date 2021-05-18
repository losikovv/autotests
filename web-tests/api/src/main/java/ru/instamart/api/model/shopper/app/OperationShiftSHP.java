package ru.instamart.api.model.shopper.app;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class OperationShiftSHP extends BaseObject {
    private Integer id;
    private Integer shopperId;
    private Integer roleId;
    private String planStartsAt;
    private String factStartsAt;
    private String planEndsAt;
    private Object factEndsAt;
    private String state;
    private RoleSHP role;
}
