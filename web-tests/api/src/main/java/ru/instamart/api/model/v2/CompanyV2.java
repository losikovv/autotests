package ru.instamart.api.model.v2;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper = false)
public class CompanyV2 extends BaseObject {
    private String inn;
    private String name;
    @JsonProperty("owner_name")
    private String ownerName;
}
