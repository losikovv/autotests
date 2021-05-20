package ru.instamart.api.response.v1.b2b;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v1.b2b.CompanyV1;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class CompaniesV1Response extends BaseResponseObject {
    private List<CompanyV1> companies;
}
