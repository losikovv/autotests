
package ru.instamart.api.model.v1;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class StoreZoneV1 extends BaseObject {

    private List<List<List<Double>>> area;
    private Long id;
    private String name;
}
