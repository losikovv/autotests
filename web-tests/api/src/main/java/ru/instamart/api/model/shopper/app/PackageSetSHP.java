package ru.instamart.api.model.shopper.app;

import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@lombok.Data
@EqualsAndHashCode(callSuper=false)
public class PackageSetSHP extends BaseObject {

    @lombok.Data
    @EqualsAndHashCode(callSuper=false)
    public static class Data extends BaseObject {
        private String id;
        private String type;
        private Attributes attributes;
        private Relationships relationships;

        @lombok.Data
        @EqualsAndHashCode(callSuper=false)
        public static class Attributes extends BaseObject {
            private String location;
            private Object qty;
            private String number;
            private String boxNumber;
        }

        @lombok.Data
        @EqualsAndHashCode(callSuper=false)
        public static class Relationships extends BaseObject {
            private AssemblySHP assembly;
        }
    }
}
