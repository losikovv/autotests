package ru.instamart.api.model.self_fee;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Null;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class Reversal {

    @JsonProperty("has_failed_registry")
    private boolean hasFailedRegistry;

    @JsonProperty("registries")
    @Null
    private List<RegistriesItem> registries;

    @JsonProperty("total_sum")
    private String totalSum;

    @JsonProperty("receipt_count")
    private int receiptCount;
}