
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class HeadV1 extends BaseObject {

    @JsonSchema
    private String description;

    @JsonSchema
    @JsonProperty("social_feed_image")
    private String socialFeedImage;

    @JsonSchema
    @JsonProperty("social_feed_image_vk")
    private String socialFeedImageVk;

    @JsonSchema
    private String title;
}
