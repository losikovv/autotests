package ru.instamart.redis;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
public enum Redis {
    ETA(
            "paas-content-operations-eta",
            "statefulset.kubernetes.io/pod-name=redis-persistent-0",
            6379,
            "localhost"
    );

    private final String namespace;
    private final String label;
    private final int containerPort;
    private final String url;
    @Setter
    private int internalPort;
}
