package ru.instamart.api.model.shopper.app;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ReplacementSHP extends BaseObject {

    @lombok.Data
    @EqualsAndHashCode(callSuper=false)
    public static class Data extends BaseObject {
        private String id;
        private String type;
        private Attributes attributes;

        @lombok.Data
        @EqualsAndHashCode(callSuper=false)
        public static class Attributes extends BaseObject {
            private Integer fromItemId;
            private Integer toItemId;
            private String reason;
        }
    }
}
