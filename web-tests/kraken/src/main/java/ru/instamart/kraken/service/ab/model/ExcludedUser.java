package ru.instamart.kraken.service.ab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public final class ExcludedUser {

    private String anonymousID;
    private Integer ttl;
}
