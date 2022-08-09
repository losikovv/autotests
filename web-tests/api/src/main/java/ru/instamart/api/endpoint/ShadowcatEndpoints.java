package ru.instamart.api.endpoint;

public class ShadowcatEndpoints {
    public static final String PROMO = "promo";
    public static final String PROMOCODES = "promocode";
    public static final String PROMOACTION = "promo/{promoId}";

    public static class PromotionCondition {
        public static final String CONDITION = "promo/{promotion_id}/conditions/{condition}";
        public static final String CONDITION_LIST = "promo/{promotion_id}/conditions/{condition}/list";
    }

    public static class Promocode {
        public static final String BY_ID = "promocode/{promocodeId}";
    }

}
