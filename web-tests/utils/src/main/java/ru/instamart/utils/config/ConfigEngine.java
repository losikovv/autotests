package ru.instamart.utils.config;

import lombok.extern.slf4j.Slf4j;
import ru.instamart.utils.Crypt;
import ru.instamart.utils.FileUtils;

import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.charset.StandardCharsets;

import static java.util.Objects.nonNull;

@Slf4j
public final class ConfigEngine {

    private static final ExProperties PROPERTIES = new ExProperties();

    /**
     * Считывает данные из файла и проверяет наличие в классе полей аннотированных {@link Config}
     * после чего через рефлексию получает к ним доступ и задает значение в соответствии с данными полученными
     * из конфигурационного файла, в случае если в конфигурационном файле, не была найдена пара ключ=значение
     * соответствующая данным из аннотации {@link Config} значение для этой переменной задается из указанного
     * дефолтного значение {@link Config#defaultValue()}
     * В случае если файл не был найден, выпадает {@link Exception}
     * В случае если не удалось получить значение из файла, бросается {@link NumberFormatException}
     * В качестве аргументов получает:
     *
     * @param configClass - класс в котором ищутся поля аннотированные {@link Config}
     * @param configName  - название конфигурационного файла
     */
    public void loadConfig(final Class<?> configClass, String configName, String configDir) {
        var env = configClass.getAnnotation(Env.class);
        if (nonNull(env)) {
            var envProperty = System.getProperty("env", "sbermarket-preprod");
            var path = envProperty.split("-");
            if (path.length == 2) {
                configDir += "/" + path[0];
                configName = path[1];
            }
        }
        var filePath = configDir + "/" + configName + ".properties";
        try (var lnr = new LineNumberReader(new InputStreamReader(FileUtils.getConfig(filePath, configClass), StandardCharsets.UTF_8))) {
            PROPERTIES.load(lnr);

            for (var field : configClass.getDeclaredFields()) {
                Config annotation;
                if (nonNull((annotation = field.getAnnotation(Config.class)))) {
                    field.setAccessible(true);

                    var fieldName = annotation.fieldName();
                    var type = field.getType().getSimpleName().toLowerCase();
                    var defaultValue = System.getProperty(annotation.args(), annotation.defaultValue());
                    var isCrypted = annotation.crypted();

                    if (isCrypted && (nonNull(defaultValue) && !defaultValue.isEmpty())) {
                        defaultValue = Crypt.INSTANCE.decrypt(defaultValue);
                    }

                    try {
                        switch (type) {
                            case "boolean":
                                var defaultBool = defaultValue.isEmpty() ? "false" : defaultValue;
                                field.setBoolean(null, PROPERTIES.getBooleanProperty(fieldName, defaultBool));
                                break;
                            case "byte":
                                var defaultByte = defaultValue.isEmpty() ? "0" : defaultValue;
                                field.setByte(null, PROPERTIES.getByteProperty(fieldName, defaultByte));
                                break;
                            case "int":
                                var defaultInt = defaultValue.isEmpty() ? "0" : defaultValue;
                                field.setInt(null, PROPERTIES.getIntProperty(fieldName, defaultInt));
                                break;
                            case "long":
                                var defaultLong = defaultValue.isEmpty() ? "0" : defaultValue;
                                field.setLong(null, PROPERTIES.getLongProperty(fieldName, defaultLong));
                                break;
                            case "float":
                                var defaultFloat = defaultValue.isEmpty() ? "0.0" : defaultValue;
                                field.setFloat(null, PROPERTIES.getFloatProperty(fieldName, defaultFloat));
                                break;
                            case "double":
                                var defaultDouble = defaultValue.isEmpty() ? "0.0" : defaultValue;
                                field.setDouble(null, PROPERTIES.getDoubleProperty(fieldName, defaultDouble));
                                break;
                            case "string":
                                var value = PROPERTIES.getProperty(fieldName, defaultValue);
                                field.set(null, isCrypted ? Crypt.INSTANCE.decrypt(value) : value);
                                break;
                            default:
                                log.info("Unknown field type: " + field.getType().getSimpleName() + " field name: " + field.getName() + " config: " + configName + ".properties");
                                break;
                        }
                    } catch (NumberFormatException e) {
                        log.error("Failed to Load " + filePath + " file. Field: " + field.getName() + " " + e.getMessage());
                    }
                    log.debug(configName + ": set " + field.getName() + "{" + fieldName + "} = " + field.get(null));
                }
            }
        } catch (Exception e) {
            log.error("Failed to Load " + filePath + " file.", e);
        }
    }
}
