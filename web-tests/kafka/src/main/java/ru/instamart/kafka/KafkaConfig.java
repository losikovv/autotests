package ru.instamart.kafka;

import lombok.Builder;
import lombok.NonNull;

@Builder
public class KafkaConfig {
    @NonNull
    public String clientId;
    @NonNull
    public String topic;
    @NonNull
    public String groupName;

    public String login;
    public String password;
}
