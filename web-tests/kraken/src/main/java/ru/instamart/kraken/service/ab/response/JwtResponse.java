package ru.instamart.kraken.service.ab.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public final class JwtResponse {

    private String access;
    private String refresh;
}
