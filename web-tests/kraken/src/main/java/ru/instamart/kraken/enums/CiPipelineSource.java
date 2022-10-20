package ru.instamart.kraken.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Objects;

@RequiredArgsConstructor
@Getter
public enum CiPipelineSource {

    PUSH("push"),
    WEB("web"),
    SCHEDULE("schedule"),
    API("api"),
    EXTERNAL("external"),
    CHAT("chat"),
    WEBIDE("webide"),
    MERGE_REQUEST_EVENT("merge_request_event"),
    EXTERNAL_PULL_REQUEST_EVENT("external_pull_request_event"),
    PARENT_PIPELINE("parent_pipeline"),
    TRIGGER("trigger"),
    PIPELINE("pipeline"),
    LOCAL("local");

    public static final CiPipelineSource CI_PIPELINE_SOURCE = getValue(System.getenv("CI_PIPELINE_SOURCE"));
    private final String name;

    public static CiPipelineSource getValue(final String constant) {
        if (Objects.isNull(constant)) {
            return LOCAL;
        }
        return Arrays.stream(CiPipelineSource.values())
                .filter(e -> e.name().equalsIgnoreCase(constant))
                .findFirst()
                .orElse(LOCAL);
    }

    public static boolean isLocal() {
        return CI_PIPELINE_SOURCE == LOCAL;
    }

    public static boolean isCustom() {
        return CI_PIPELINE_SOURCE == WEB;
    }
}
