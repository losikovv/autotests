package ru.instamart.api.model.v2.recs;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper = false)
public class ExtV2 extends BaseResponseObject {
    private String recommendationType;
    private Integer componentId;
    private String recommendationAlgorithm;
}