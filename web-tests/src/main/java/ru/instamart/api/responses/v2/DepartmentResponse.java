package ru.instamart.api.responses.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.instamart.api.objects.BaseObject;
import ru.instamart.api.objects.v2.Aisles;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public final class DepartmentResponse extends BaseObject {
    private Department department;

    @Data
    public static final class Department {
        private int id;
        private String type;
        private String name;
        @JsonProperty(value = "products_count")
        private int productCount;
        private List<Aisles> aisles;
    }
}
