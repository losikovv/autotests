package ru.instamart.api.model.v2;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@EqualsAndHashCode(callSuper = true)
@Data
public final class BonusCardV2 extends BaseObject {
    private Integer id;
    private String number;
    private String name;
    private IconV2 icon;
}
