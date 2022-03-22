
package ru.instamart.api.response.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v1.WelcomeBannerV1;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper = false)
public class RetailerWelcomeBannerV1Response extends BaseResponseObject {

    @JsonProperty("welcome_banner")
    private WelcomeBannerV1 welcomeBanner;
}
