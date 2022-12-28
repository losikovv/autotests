package ru.instamart.api.model.self_fee;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper = false)
public class Summary {

    @JsonProperty("reversal")
    @Null
    private Reversal reversal;

    @JsonProperty("success")
    private Success success;

    @JsonProperty("failed")
    private Failed failed;
}