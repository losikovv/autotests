package ru.instamart.api.objects.v2;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class PromotionV2 extends BaseObject {
    private String message;
}
