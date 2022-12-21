package ru.instamart.api.endpoint;

public class ShadowcatEndpoints {
    public static final String PROMO = "admin/promo";
    public static final String PROMOCODES = "admin/promocode";
    public static final String PROMOACTION = "admin/promo/{promoId}";
    public static final String CALCULATE = "promo/deals/calculate";

    public static class PromotionCondition {
        public static final String CONDITION = "admin/promo/{promotion_id}/conditions/{condition}";
        public static final String CONDITION_LIST = "admin/promo/{promotion_id}/conditions/{condition}/list";
    }

    public static class Promocode {
        public static final String BY_ID = "admin/promocode/{promocodeId}";
        public static final String CHECK = "admin/promocode/check/{promocode}";
    }

}
