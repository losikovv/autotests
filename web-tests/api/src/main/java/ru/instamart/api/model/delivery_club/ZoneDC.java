package ru.instamart.api.model.delivery_club;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ZoneDC extends BaseObject {
    private String id;
}
