package ru.instamart.api.helper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.imifou.jsonschema.module.addon.AddonModule;
import com.github.victools.jsonschema.generator.*;
import com.github.victools.jsonschema.module.jackson.JacksonModule;
import com.github.victools.jsonschema.module.javax.validation.JavaxValidationModule;

public class JsonSchemaHelper {

    public static String getJsonSchema(Class<?> clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        AddonModule module = new AddonModule();
        SchemaGeneratorConfigBuilder configBuilder = new SchemaGeneratorConfigBuilder(objectMapper, SchemaVersion.DRAFT_2019_09, OptionPreset.PLAIN_JSON)
                .with(module);
        configBuilder
                .with(new AddonModule())
                .with(Option.NONSTATIC_NONVOID_NONGETTER_METHODS)
                .with(new JacksonModule())
                .with(new JavaxValidationModule());
        SchemaGeneratorConfig config = configBuilder.build();
        SchemaGenerator generator = new SchemaGenerator(config);
        JsonNode jsonSchema = generator.generateSchema(clazz);

        return jsonSchema.toString();
    }
}
