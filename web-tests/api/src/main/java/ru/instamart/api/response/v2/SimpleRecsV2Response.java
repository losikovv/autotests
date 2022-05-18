package ru.instamart.api.response.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v2.simple_recs.ExtV2;
import ru.instamart.api.model.v2.simple_recs.MediaV2;
import ru.instamart.api.response.BaseResponseObject;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class SimpleRecsV2Response extends BaseResponseObject {

    @JsonSchema(required = true)
    @JsonProperty("req_id")
    private String reqId;

    private ExtV2 extV2;

    @JsonSchema(required = true)
    private List<MediaV2> media;
}