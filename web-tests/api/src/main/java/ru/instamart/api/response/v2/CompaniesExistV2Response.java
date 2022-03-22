package ru.instamart.api.response.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.response.BaseResponseObject;

@EqualsAndHashCode(callSuper = true)
@Data
public class CompaniesExistV2Response extends BaseResponseObject {

    @JsonProperty("companies_exist")
    private boolean companiesExist;
}