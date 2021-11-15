package ru.instamart.api.response.v2;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v2.simple_recs.ExtV2;
import ru.instamart.api.model.v2.simple_recs.MediaV2;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class SimpleRecsV2Response extends BaseResponseObject {
    private ExtV2 extV2;
    private List<MediaV2> media;
}