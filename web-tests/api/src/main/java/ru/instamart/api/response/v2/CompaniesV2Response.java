package ru.instamart.api.response.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v2.CompanyV2;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class CompaniesV2Response extends BaseResponseObject {

    @JsonProperty("companies")
    private List<CompanyV2> companies;
}