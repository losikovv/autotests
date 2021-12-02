package ru.instamart.api.response.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v2.AbTestV2;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class AbTestsV2Response extends BaseResponseObject {

    @JsonProperty("ab_tests")
    private List<AbTestV2> abTests;
}