package ru.instamart.kraken.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
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

    private final String name;
}
