package ru.instamart.ab.model.response;

import lombok.Data;

@Data
public final class JwtResponse {

    private String access;
    private String refresh;
}
