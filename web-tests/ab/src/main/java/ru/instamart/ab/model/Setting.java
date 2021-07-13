package ru.instamart.ab.model;

import lombok.Data;

@Data
public final class Setting {

    private final String basicUrl;
    private final String email;
    private final String password;
}
