
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class UrlsV1 extends BaseObject {

    @JsonSchema(required = true)
    @JsonProperty("admin_order_customer")
    private String adminOrderCustomer;

    @JsonSchema(required = true)
    @JsonProperty("admin_order_delivery_windows")
    private String adminOrderDeliveryWindows;

    @JsonSchema(required = true)
    @JsonProperty("admin_order_payments")
    private String adminOrderPayments;

    @JsonSchema(required = true)
    @JsonProperty("edit_admin_order")
    private String editAdminOrder;

    @JsonSchema(required = true)
    private String store;

    @JsonSchema(required = true)
    @JsonProperty("xlsx_document_order")
    private String xlsxDocumentOrder;
}
