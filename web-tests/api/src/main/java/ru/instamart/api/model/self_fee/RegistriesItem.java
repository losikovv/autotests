package ru.instamart.api.model.self_fee;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class RegistriesItem {

    @JsonProperty("filename")
    private String filename;

    @JsonProperty("created_at")
    private int createdAt;

    @JsonProperty("id")
    private int id;

    @JsonProperty("status")
    private String status;
}