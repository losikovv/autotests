package instamart.api.helpers;

import instamart.api.objects.v3.StoreV3;
import instamart.api.requests.v3.StoresV3Request;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class ApiV3Helper {

    private static final Logger log = LoggerFactory.getLogger(InstamartApiHelper.class);

    public List<StoreV3> getPickupStores() {
        log.info("Получаем список магазинов с самовызом");
        return Arrays.asList(StoresV3Request.PickupFromStore.GET().as(StoreV3[].class));
    }

    public StoreV3 getStore(String name) {
        log.info("Ищем магазин по имени: " + name);
        List<StoreV3> stores = getPickupStores();
        for (StoreV3 store : stores) {
            if (name.equals(store.getName())) {
                log.info("Найден магазин: " + store);
                return store;
            }
        }
        Assert.fail("Не найден нужный магазин");
        return stores.get(0);
    }

}
