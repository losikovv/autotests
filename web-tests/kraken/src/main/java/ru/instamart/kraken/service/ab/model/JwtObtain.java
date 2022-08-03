package ru.instamart.kraken.service.ab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public final class JwtObtain {

    private String code;
    @JsonProperty(value = "callback_url")
    private String callback;
    private String provider;
    private String email;
    private String password;
}
