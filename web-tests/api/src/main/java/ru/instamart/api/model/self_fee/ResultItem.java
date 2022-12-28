package ru.instamart.api.model.self_fee;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ResultItem {

    @JsonProperty("summary")
    private Summary summary;

    @JsonProperty("partner_count")
    private int partnerCount;

    @JsonProperty("bank")
    private String bank;

    @JsonProperty("file")
    private File file;

    @JsonProperty("name")
    private String name;

    @JsonProperty("id")
    private int id;

    @JsonProperty("total_sum")
    private String totalSum;

    @JsonProperty("receipt_count")
    private int receiptCount;

    @JsonProperty("status")
    private String status;
}