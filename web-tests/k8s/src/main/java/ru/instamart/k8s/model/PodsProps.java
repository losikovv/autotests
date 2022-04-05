package ru.instamart.k8s.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public final class PodsProps {
    @NonNull
    private String nameSpace;
    @NonNull
    private String label;
    @NonNull
    private int remotePort;
}
