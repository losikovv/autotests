package ru.instamart.kafka.common;

import ru.instamart.kafka.helper.KafkaHelper;
import ru.instamart.kafka.helper.LogHelper;

public class KafkaBase {
    protected static final LogHelper kubeLog = new LogHelper();
    protected static final KafkaHelper kafka = new KafkaHelper();

}
