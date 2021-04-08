package ru.instamart.api.objects.v1;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class OperationalZoneV1 extends BaseObject {
    private Integer id;
    private String name;

    public String toString() {
        return "id: " + id + ", " + name;
    }
}
