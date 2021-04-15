package ru.instamart.api.requests.shopper.app;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoints.ShopperAppEndpoints;
import ru.instamart.api.enums.shopper.PackageSetLocationSHP;

import static ru.instamart.api.requests.ShopperRequestBase.givenWithAuth;

public final class AssembliesSHPRequest {

    /**
     * Получаем доставку
     */
    @Step("{method} /" + ShopperAppEndpoints.Assemblies.ID)
    public static Response GET(String assemblyId) {
        return givenWithAuth()
                .get(ShopperAppEndpoints.Assemblies.ID, assemblyId);
    }

    /**
     * Создаем доставку
     */
    @Step("{method} /" + ShopperAppEndpoints.ASSEMBLIES)
    public static Response POST(String shipmentId) {
        return givenWithAuth()
                .formParam("shipment_id", shipmentId)
                .post(ShopperAppEndpoints.ASSEMBLIES);
    }

    /**
     * Удаляем доставку
     */
    @Step("{method} /" + ShopperAppEndpoints.Assemblies.ID)
    public static Response DELETE(String assemblyId) {
        return givenWithAuth()
                .delete(ShopperAppEndpoints.Assemblies.ID, assemblyId);
    }

    public static class Items {
        /**
         * Добавляем новый товар
         */
        @Step("{method} /" + ShopperAppEndpoints.Assemblies.ITEMS)
        public static Response POST(String assemblyId, String offerUuid, int foundQty) {
            JSONObject requestParams = new JSONObject();
            JSONObject assemblyItem = new JSONObject();
            requestParams.put("assembly_item", assemblyItem);
            assemblyItem.put("offer_uuid", offerUuid);
            assemblyItem.put("found_qty", foundQty);
            return givenWithAuth()
                    .body(requestParams)
                    .contentType(ContentType.JSON)
                    .post(ShopperAppEndpoints.Assemblies.ITEMS, assemblyId);
        }
    }
    public static class Approve {
        @Step("{method} /" + ShopperAppEndpoints.Assemblies.APPROVE)
        public static Response PATCH(String assemblyId) {
            return givenWithAuth()
                    .patch(ShopperAppEndpoints.Assemblies.APPROVE, assemblyId);
        }
    }
    public static class StartPaymentVerification {
        @Step("{method} /" + ShopperAppEndpoints.Assemblies.START_PAYMENT_VERIFICATION)
        public static Response PUT(String assemblyId) {
            return givenWithAuth()
                    .put(ShopperAppEndpoints.Assemblies.START_PAYMENT_VERIFICATION, assemblyId);
        }
    }
    public static class FinishAssembling {
        @Step("{method} /" + ShopperAppEndpoints.Assemblies.FINISH_ASSEMBLING)
        public static Response PUT(String assemblyId) {
            return givenWithAuth()
                    .put(ShopperAppEndpoints.Assemblies.FINISH_ASSEMBLING, assemblyId);
        }
    }
    public static class PackageSets {
        @Step("{method} /" + ShopperAppEndpoints.Assemblies.PACKAGE_SETS)
        public static Response GET(String assemblyId) {
            return givenWithAuth()
                    .get(ShopperAppEndpoints.Assemblies.PACKAGE_SETS, assemblyId);
        }
        /**
         * Сборщик складывает товары для передачи упаковщику
         * @param boxNumber - количество ящиков
         */
        @Step("{method} /" + ShopperAppEndpoints.Assemblies.PACKAGE_SETS)
        public static Response POST(String assemblyId, int boxNumber) {
            JSONObject requestParams = new JSONObject();
            JSONArray packageSets = new JSONArray();
            requestParams.put("package_sets", packageSets);
            for (int i = 1; i <= boxNumber; i++) {
                packageSets.add(packageSet(PackageSetLocationSHP.BASKET.getLocation(),1, i));
            }
            return givenWithAuth()
                    .body(requestParams)
                    .contentType(ContentType.JSON)
                    .post(ShopperAppEndpoints.Assemblies.PACKAGE_SETS, assemblyId);
        }

        /**
         * Упаковщик складывает товары для передачи курьеру
         * @param basket - кол-во товаров в ящике
         * @param rack - кол-во товаров на стеллаже
         * @param fridge - кол-во товаров в холодильнике
         * @param freezer - кол-во товаров в морозилке
         */
        @Step("{method} /" + ShopperAppEndpoints.Assemblies.PACKAGE_SETS)
        public static Response POST(
                String assemblyId,
                int basket,
                int rack,
                int fridge,
                int freezer) {
            JSONObject requestParams = new JSONObject();
            JSONArray packageSets = new JSONArray();
            requestParams.put("package_sets", packageSets);
            if (basket != 0) packageSets.add(packageSet(PackageSetLocationSHP.BASKET, basket));
            if (rack != 0) packageSets.add(packageSet(PackageSetLocationSHP.RACK, rack));
            if (fridge != 0) packageSets.add(packageSet(PackageSetLocationSHP.FRIDGE, fridge));
            if (freezer != 0) packageSets.add(packageSet(PackageSetLocationSHP.FREEZER, freezer));
            return givenWithAuth()
                    .body(requestParams)
                    .contentType(ContentType.JSON)
                    .post(ShopperAppEndpoints.Assemblies.PACKAGE_SETS, assemblyId);
        }

        private static JSONObject packageSet(PackageSetLocationSHP packageSetLocation, int number) {
            return packageSet(packageSetLocation.getLocation(), number, packageSetLocation.getBoxNumber());
        }

        private static JSONObject packageSet(String location, int number, int boxNumber){
            JSONObject packageSet = new JSONObject();
            packageSet.put("location", location);
            packageSet.put("number", number);
            packageSet.put("box_number", boxNumber);
            return packageSet;
        }
    }
    public static class Packer {
        @Step("{method} /" + ShopperAppEndpoints.Assemblies.PACKER)
        public static Response PUT(String assemblyId) {
            return givenWithAuth()
                    .put(ShopperAppEndpoints.Assemblies.PACKER, assemblyId);
        }
    }
    public static class StartPurchasing {
        @Step("{method} /" + ShopperAppEndpoints.Assemblies.START_PURCHASING)
        public static Response PUT(String assemblyId) {
            return givenWithAuth()
                    .put(ShopperAppEndpoints.Assemblies.START_PURCHASING, assemblyId);
        }
    }
    public static class Receipts {
        @Step("{method} /" + ShopperAppEndpoints.Assemblies.RECEIPTS)
        public static Response POST(
                String assemblyId,
                String total,
                String fiscalSecret,
                String fiscalDocumentNumber,
                String fiscalChecksum,
                String paidAt,
                String transactionDetails) {
            JSONObject requestParams = new JSONObject();
            requestParams.put("total", total);
            requestParams.put("fiscal_secret", fiscalSecret);
            requestParams.put("fiscal_document_number", fiscalDocumentNumber);
            requestParams.put("fiscal_checksum", fiscalChecksum);
            requestParams.put("paid_at", paidAt);
            requestParams.put("transaction_details", transactionDetails);
            return givenWithAuth()
                    .body(requestParams)
                    .contentType(ContentType.JSON)
                    .post(ShopperAppEndpoints.Assemblies.RECEIPTS, assemblyId);
        }
    }
    public static class StartPackaging {
        @Step("{method} /" + ShopperAppEndpoints.Assemblies.START_PACKAGING)
        public static Response PATCH(String assemblyId) {
            return givenWithAuth()
                    .patch(ShopperAppEndpoints.Assemblies.START_PACKAGING, assemblyId);
        }
    }
    public static class Purchase {
        @Step("{method} /" + ShopperAppEndpoints.Assemblies.PURCHASE)
        public static Response PATCH(String assemblyId) {
            return givenWithAuth()
                    .patch(ShopperAppEndpoints.Assemblies.PURCHASE, assemblyId);
        }
    }
    public static class LifePay {
        @Step("{method} /" + ShopperAppEndpoints.Assemblies.LIFEPAY)
        public static Response PUT(String assemblyId) {
            return givenWithAuth()
                    .put(ShopperAppEndpoints.Assemblies.LIFEPAY, assemblyId);
        }
    }
    public static class Pause {
        @Step("{method} /" + ShopperAppEndpoints.Assemblies.PAUSE)
        public static Response PATCH(String assemblyId) {
            return givenWithAuth()
                    .patch(ShopperAppEndpoints.Assemblies.PAUSE, assemblyId);
        }
    }
    public static class Suspend {
        @Step("{method} /" + ShopperAppEndpoints.Assemblies.SUSPEND)
        public static Response PATCH(String assemblyId) {
            return givenWithAuth()
                    .patch(ShopperAppEndpoints.Assemblies.SUSPEND, assemblyId);
        }
    }
    public static class Ship {
        @Step("{method} /" + ShopperAppEndpoints.Assemblies.SHIP)
        public static Response PATCH(String assemblyId) {
            return givenWithAuth()
                    .patch(ShopperAppEndpoints.Assemblies.SHIP, assemblyId);
        }
    }
    public static class Trolleys {
        //todo
    }
    public static class ApproveNeedReviewItems {
        //todo
    }
}
