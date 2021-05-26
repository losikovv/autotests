package ru.instamart.api.enums.v2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public enum AuthProviderV2 {
    METRO("metro", "123123123", "$2y$12$s/EsWQhjtIDe2XYCghPDZOZq21ora.ety8.1d/35a2qyKrILkKS.a"),
    SBERAPP("sberapp", "111000", "$2y$12$pmOBY91.fqcwTnRdSzG0WuWhYD4skzAg1vw4HKm.vL1nekpNsj/D."),
    VKONTAKTE("vkontakte", "553607487", null),
    FACEBOOK("facebook", "2509563982470943", null),

    WRONG_ID("wrong", null, null);

    private final String id;
    private final String sessionUid;
    private final String sessionDigest;

    @Override
    public String toString() {
        return id;
    }
}
