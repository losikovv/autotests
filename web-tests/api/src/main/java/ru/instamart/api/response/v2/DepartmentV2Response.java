package ru.instamart.api.response.v2;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;
import ru.instamart.api.model.v2.DepartmentWithAislesV2;

@Data
@EqualsAndHashCode(callSuper=false)
public final class DepartmentV2Response extends BaseObject {
    private DepartmentWithAislesV2 department;
}
