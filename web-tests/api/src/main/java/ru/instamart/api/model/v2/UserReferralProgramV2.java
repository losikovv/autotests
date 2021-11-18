
package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class UserReferralProgramV2 extends BaseObject {

    private String code;
    @JsonProperty("share_links")
    private List<ShareLinkV2> shareLinks;
}
