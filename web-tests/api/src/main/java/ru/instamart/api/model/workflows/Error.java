package ru.instamart.api.model.workflows;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class Error extends BaseObject {

    private Integer status;
    private String title;
    private String type;
}
