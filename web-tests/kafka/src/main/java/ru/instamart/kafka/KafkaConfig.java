package ru.instamart.kafka;

import io.confluent.kafka.serializers.protobuf.KafkaProtobufDeserializer;

public class KafkaConfig {
//    public Set<HostAndPort> hosts = set(
//            new HostAndPort("127.0.0.1", 9092)
//    );

    public String topic = "yc.operations-order-service.fct.order-enrichment.0";

    public String groupName = "dispatch_";

    public String serdeClassName = KafkaProtobufDeserializer.class.getName();


    // producer only config

    public boolean exactlyOnce = true;

    public String acks = "1"; // or "all";

    public int retries = 1;

    public String bootstrapServerString() {
        return "84.201.149.206:9094";
    }
}
