package ru.instamart.api.responses.v2;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.v2.DealsV2;
import ru.instamart.api.objects.v2.DepartmentV2;
import ru.instamart.api.responses.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class DepartmentsV2Response extends BaseResponseObject {
    private List<DepartmentV2> departments = null;
    private DealsV2 deals;
}