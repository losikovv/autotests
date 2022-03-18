package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper = false)
public class CompaniesItemV2 extends BaseObject {

    @JsonProperty("inn")
    private String inn;

    @JsonProperty("name")
    private String name;

    @JsonProperty("id")
    private int id;
}