package ru.instamart.kafka;

import lombok.Builder;
import lombok.NonNull;

@Builder
public class KafkaConfig {
    @NonNull
    public String topic;
    public String login;
    public String password;
}
