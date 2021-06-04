package ru.instamart.api.response.v1.b2b;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v1.b2b.EmployeeV1;
import ru.instamart.api.model.v1.MetaV1;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class EmployeesV1Response extends BaseResponseObject {
    private MetaV1 meta;
    private List<EmployeeV1> employees;
}
