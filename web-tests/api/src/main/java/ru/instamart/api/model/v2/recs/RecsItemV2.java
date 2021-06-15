package ru.instamart.api.model.v2.recs;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper = false)
public class RecsItemV2 extends BaseResponseObject {
    private ExtV2 ext;
    private MediaV2 media;
    private String placementId;
}