package ru.instamart.api.helper;

import ru.instamart.jdbc.dao.PromotionCodesDao;
import ru.instamart.jdbc.dto.PromotionCodesFilters;
import ru.instamart.jdbc.entity.PromotionCodesEntity;

import java.util.List;


public class PromotionCode {
    public static String getPromotionCode() {
        PromotionCodesFilters filters = PromotionCodesFilters.builder()
                .value("auto%")
                .usageLimit(5000)
                .limit(1)
                .build();
        List<PromotionCodesEntity> allPromoCodes = PromotionCodesDao.INSTANCE.findAll(filters);
        if(allPromoCodes.size()!=0) {
            return allPromoCodes.get(0).getValue();
        }
        return "auto300lomxs4"; //default promo code
    }


    public static String getExpiredPromotionCode() {
        PromotionCodesFilters expiredFilters = PromotionCodesFilters.builder()
                .value("auto%")
                .usageLimit(0)
                .limit(1)
                .build();
        List<PromotionCodesEntity> allExpiredPromoCodes = PromotionCodesDao.INSTANCE.findAll(expiredFilters);
        if(allExpiredPromoCodes.size()!=0) {
            return allExpiredPromoCodes.get(0).getValue();
        } else {
            PromotionCodesFilters filters = PromotionCodesFilters.builder()
                    .value("auto%")
                    .usageLimit(5000)
                    .limit(2)
                    .build();
            List<PromotionCodesEntity> allPromoCodes = PromotionCodesDao.INSTANCE.findAll(filters);
            if(allPromoCodes.size() > 0) {
                String promoCode = allPromoCodes.get(1).getValue();
                PromotionCodesDao.INSTANCE.updateUsageLimit(0, promoCode);
                return promoCode;
            }
            return null;
        }
    }
}
