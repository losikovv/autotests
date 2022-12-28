package ru.instamart.api.model.self_fee;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Success {

    @JsonProperty("total_sum")
    private String totalSum;

    @JsonProperty("receipt_count")
    private int receiptCount;
}