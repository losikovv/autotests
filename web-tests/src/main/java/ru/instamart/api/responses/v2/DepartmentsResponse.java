package ru.instamart.api.responses.v2;

import ru.instamart.api.responses.BaseResponseObject;
import ru.instamart.api.objects.v2.Deals;
import ru.instamart.api.objects.v2.Department;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class DepartmentsResponse extends BaseResponseObject {
    private List<Department> departments = null;
    private Deals deals;
}