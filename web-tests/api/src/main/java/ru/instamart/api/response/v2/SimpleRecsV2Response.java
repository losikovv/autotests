package ru.instamart.api.response.v2;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.response.BaseResponseObject;
import ru.instamart.api.model.v2.simple_recs.ExtV2;
import ru.instamart.api.model.v2.simple_recs.MediaItemV2;

@Data
@EqualsAndHashCode(callSuper = false)
public class SimpleRecsV2Response extends BaseResponseObject {
    private ExtV2 extV2;
    private List<MediaItemV2> media;
}