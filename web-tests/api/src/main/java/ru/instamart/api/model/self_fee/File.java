package ru.instamart.api.model.self_fee;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class File {

    @JsonProperty("filename")
    private String filename;

    @JsonProperty("id")
    private String id;
}