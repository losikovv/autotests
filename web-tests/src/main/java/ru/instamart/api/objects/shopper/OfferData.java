package instamart.api.objects.shopper;

import instamart.api.objects.BaseObject;

public class OfferData extends BaseObject {

        private String id;
        private String type;
        private OfferAttributes attributes;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public OfferAttributes getAttributes() {
            return attributes;
        }

        public void setAttributes(OfferAttributes attributes) {
            this.attributes = attributes;
        }

    }
