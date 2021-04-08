package ru.instamart.api.objects.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class MasterCategory extends BaseObject {
    private Integer id;
    private String name;
    @JsonProperty(value = "special_condition")
    private Object specialCondition;
    @JsonProperty(value = "master_category_attributes")
    private List<Object> masterCategoryAttributes = null;
}
