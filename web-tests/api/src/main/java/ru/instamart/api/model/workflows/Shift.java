package ru.instamart.api.model.workflows;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class Shift extends BaseObject {
    private Long id;
    private Integer transport;
}
