package ru.instamart.api.helper;

import com.github.imifou.jsonschema.module.addon.AddonModule;
import com.github.victools.jsonschema.generator.*;
import com.github.victools.jsonschema.module.jackson.JacksonModule;
import com.github.victools.jsonschema.module.javax.validation.JavaxValidationModule;
import ru.instamart.kraken.common.Mapper;

public final class JsonSchemaHelper {

    private static final SchemaGeneratorConfig config = new SchemaGeneratorConfigBuilder(Mapper.INSTANCE.getObjectMapper(), SchemaVersion.DRAFT_2019_09, OptionPreset.PLAIN_JSON)
            .with(new AddonModule())
            .with(Option.NONSTATIC_NONVOID_NONGETTER_METHODS)
            .with(new JacksonModule())
            .with(new JavaxValidationModule())
            .build();
    private static final SchemaGenerator generator = new SchemaGenerator(config);

    public static String getJsonSchema(final Class<?> clazz) {
        return generator.generateSchema(clazz).toString();
    }

    private JsonSchemaHelper() {}
}
