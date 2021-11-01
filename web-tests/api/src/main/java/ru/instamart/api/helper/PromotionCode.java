package ru.instamart.api.helper;

import ru.instamart.api.k8s.K8sPortForward;
import ru.instamart.jdbc.dao.PromotionCodesDao;
import ru.instamart.jdbc.dto.PromotionCodesFilters;

import java.util.List;


public class PromotionCode {
    public static String getPromotionCode() {
        PromotionCodesFilters filters = PromotionCodesFilters.builder()
                .value("auto%")
                .limit(1)
                .build();
        List<String> allPromoCodes = PromotionCodesDao.INSTANCE.findAll(filters);
        if(allPromoCodes.size()!=0) {
            return allPromoCodes.get(0);
        }
        return "auto300lomxs4"; //default promo code
    }
}
