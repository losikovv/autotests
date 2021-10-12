package ru.instamart.api.model.v2.app_config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper = false)
public class AuthenticationV2 extends BaseObject {
    @JsonProperty(value = "cipher_key_phone")
    private String cipherKeyPhone;
}
