package instamart.api.enums.v2;

public enum AuthProvider {
    METRO(
            "metro",
            "123123123",
            "$2y$12$s/EsWQhjtIDe2XYCghPDZOZq21ora.ety8.1d/35a2qyKrILkKS.a"),
    SBERAPP(
            "sberapp",
            "111000",
            "$2y$12$pmOBY91.fqcwTnRdSzG0WuWhYD4skzAg1vw4HKm.vL1nekpNsj/D."),
    VKONTAKTE(
            "vkontakte",
            "553607487",
            null),
    FACEBOOK(
            "facebook",
            "2509563982470943",
            null);

    private final String id;
    private final String sessionUid;
    private final String sessionDigest;

    AuthProvider(String id, String sessionUid, String sessionDigest) {
        this.id = id;
        this.sessionUid = sessionUid;
        this.sessionDigest = sessionDigest;
    }

    public String getId() {
        return id;
    }

    public String getSessionUid() {
        return sessionUid;
    }

    public String getSessionDigest() {
        return sessionDigest;
    }

    public String toString() {
        return id;
    }
}
