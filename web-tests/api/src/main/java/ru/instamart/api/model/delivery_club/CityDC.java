package ru.instamart.api.model.delivery_club;

import ru.instamart.api.model.BaseObject;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Builder
public class CityDC extends BaseObject {
    private String name;
    private String code;
}
