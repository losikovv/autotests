package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper = false)
public class PhoneV2 extends BaseObject {
    private String number;
    private String code;
    @JsonProperty("human_number")
    private String humanNumber;
    private Integer id;
}