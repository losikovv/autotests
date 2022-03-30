package ru.instamart.api.response.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.response.BaseResponseObject;
import ru.instamart.api.model.v2.NoSberIdV2;
import ru.instamart.api.model.v2.NotRegisteredV2;
import ru.instamart.api.model.v2.RegisteredV2;

@EqualsAndHashCode(callSuper = true)
@Data
public class RegisteredV2Response extends BaseResponseObject {

    @JsonProperty("no_sber_id")
    private NoSberIdV2 noSberId;

    @JsonProperty("registered")
    private RegisteredV2 registered;

    @JsonProperty("not_registered")
    private NotRegisteredV2 notRegistered;
}