package ru.instamart.k8s.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class PodsProps {
    private String nameSpace;
    private String label;
    private int remotePort;
}
