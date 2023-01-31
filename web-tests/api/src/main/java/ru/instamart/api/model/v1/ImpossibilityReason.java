package ru.instamart.api.model.v1;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper = false)
public class ImpossibilityReason extends BaseObject {

    private String code;
}
