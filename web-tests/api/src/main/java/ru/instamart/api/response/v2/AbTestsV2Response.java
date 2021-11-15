package ru.instamart.api.response.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;
import ru.instamart.api.model.v2.AbTestV2;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class AbTestsV2Response extends BaseObject {

    @JsonProperty("ab_tests")
    private List<AbTestV2> abTests;
}