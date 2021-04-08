package ru.instamart.api.objects.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Adjustment extends BaseObject {
    private Integer id;
    @JsonProperty(value = "source_type")
    private Object sourceType;
    @JsonProperty(value = "source_id")
    private Object sourceId;
    @JsonProperty(value = "adjustable_type")
    private String adjustableType;
    @JsonProperty(value = "adjustable_id")
    private Integer adjustableId;
    @JsonProperty(value = "originator_type")
    private String originatorType;
    @JsonProperty(value = "originator_id")
    private Integer originatorId;
    private Double amount;
    private String label;
    private Boolean mandatory;
    private Boolean eligible;
    @JsonProperty(value = "created_at")
    private String createdAt;
    @JsonProperty(value = "updated_at")
    private String updatedAt;
}
