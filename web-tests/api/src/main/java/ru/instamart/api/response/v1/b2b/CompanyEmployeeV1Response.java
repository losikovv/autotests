
package ru.instamart.api.response.v1.b2b;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v1.b2b.EmployeeV1;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class CompanyEmployeeV1Response extends BaseResponseObject {

    @JsonSchema(required = true)
    private EmployeeV1 employee;
}
