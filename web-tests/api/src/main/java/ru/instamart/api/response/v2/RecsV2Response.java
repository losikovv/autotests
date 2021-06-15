package ru.instamart.api.response.v2;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v2.recs.RecsItemV2;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper = false)
public class RecsV2Response extends BaseResponseObject {
    private String reqId;
    private List<RecsItemV2> recs;
}