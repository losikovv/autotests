package ru.instamart.api.model.shopper.app;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ReplacementAttributesSHP extends BaseObject {
    private Integer fromItemId;
    private Integer toItemId;
    private String reason;
}
