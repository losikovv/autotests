package ru.instamart.api.response.ris_exporter;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.ris_exporter.StockRis;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class StockRisResponse extends BaseResponseObject {
    @JsonSchema(required = true)
    private Integer totalCount;
    @JsonSchema(required = true)
    private List<StockRis> data;
}
