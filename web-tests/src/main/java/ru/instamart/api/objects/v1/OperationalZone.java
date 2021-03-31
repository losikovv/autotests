package instamart.api.objects.v1;

import instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class OperationalZone extends BaseObject {
    private Integer id;
    private String name;

    public String toString() {
        return "id: " + id + ", " + name;
    }
}
