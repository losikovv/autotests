
package ru.instamart.api.model.v3;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ErrorV3 extends BaseObject {
    private Object field;
    private String message;
    private String type;
}
