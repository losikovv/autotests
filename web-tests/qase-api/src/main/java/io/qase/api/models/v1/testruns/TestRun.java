package io.qase.api.models.v1.testruns;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@SuppressWarnings("unused")
public class TestRun {
    private String description;
    @SerializedName("end_time")
    private LocalDateTime endTime;
    private Environment environment;
    private long id;
    @SerializedName("public")
    private Boolean isPublic;
    @SerializedName("start_time")
    private LocalDateTime startTime;
    private Stats stats;
    private long status;
    @SerializedName("status_text")
    private String statusText;
    @SerializedName("time_spent")
    private long timeSpent;
    private String title;
    @SerializedName("user_id")
    private long userId;
    private List<Integer> cases;
}
