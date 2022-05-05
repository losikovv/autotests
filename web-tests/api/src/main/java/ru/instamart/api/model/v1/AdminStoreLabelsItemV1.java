package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.instamart.api.model.BaseObject;

@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdminStoreLabelsItemV1 extends BaseObject {

    @JsonProperty("code")
    private String code;

    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonProperty("level")
    private int level;

    @JsonProperty("subtitle")
    private String subtitle;

    @JsonProperty("icon")
    private String icon;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("id")
    private int id;

    @JsonProperty("position")
    private int position;

    @JsonProperty("title")
    private String title;

    @JsonProperty("uuid")
    private String uuid;
}