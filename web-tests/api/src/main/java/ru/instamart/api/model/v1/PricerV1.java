package ru.instamart.api.model.v1;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class PricerV1 extends BaseObject {

    @JsonSchema(required = true)
    private Integer id;

    @JsonSchema(required = true)
    private Integer position;

    @Null
    @JsonSchema(required = true)
    private CalculatorV1 calculator;

    @JsonSchema(required = true)
    private List<RulesV1> rules;
}
