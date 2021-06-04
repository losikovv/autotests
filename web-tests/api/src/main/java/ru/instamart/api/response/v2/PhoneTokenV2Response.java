package ru.instamart.api.response.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v2.PhoneTokenV2;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class PhoneTokenV2Response extends BaseResponseObject {
    @JsonProperty(value = "phone_token")
    private PhoneTokenV2 phoneToken;
}
