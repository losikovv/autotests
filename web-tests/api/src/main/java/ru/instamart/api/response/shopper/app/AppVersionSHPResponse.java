package ru.instamart.api.response.shopper.app;

import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;
import ru.instamart.api.response.BaseResponseObject;

@lombok.Data
@EqualsAndHashCode(callSuper=false)
public class AppVersionSHPResponse extends BaseResponseObject {
    private Data data;

    @lombok.Data
    @EqualsAndHashCode(callSuper=false)
    public static class Data extends BaseObject {
        private String id;
        private String type;
        private Attributes attributes;

        @lombok.Data
        @EqualsAndHashCode(callSuper=false)
        public static class Attributes extends BaseObject {
            private Integer major;
            private String changelog;
            private String importance;
            private String downloadUrl;
        }
    }
}
