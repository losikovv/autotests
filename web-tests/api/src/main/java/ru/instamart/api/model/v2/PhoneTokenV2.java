package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class PhoneTokenV2 extends BaseObject {
    @JsonProperty(value = "resend_limit")
    private Integer resendLimit;
    @JsonProperty(value = "code_length")
    private Integer codeLength;
}
