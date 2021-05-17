package ru.instamart.api.response.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;
import ru.instamart.api.model.v2.AislesV2;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public final class DepartmentV2Response extends BaseObject {
    private Department department;

    @Data
    public static final class Department {
        private int id;
        private String type;
        private String name;
        @JsonProperty(value = "products_count")
        private int productCount;
        private List<AislesV2> aisles;
    }
}
