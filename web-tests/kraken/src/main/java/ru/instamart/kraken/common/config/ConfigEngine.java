package ru.instamart.kraken.common.config;

import lombok.extern.slf4j.Slf4j;
import ru.instamart.kraken.common.Crypt;
import ru.instamart.kraken.util.FileUtils;

import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.charset.StandardCharsets;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
public final class ConfigEngine {

    private static final ExProperties PROPERTIES = new ExProperties();

    /**
     * Считывает данные из файла и проверяет наличие в классе полей аннотированных {@link ru.sbermarket.common.config.Config}
     * после чего через рефлексию получает к ним доступ и задает значение в соответствии с данными полученными
     * из конфигурационного файла, в случае если в конфигурационном файле, не была найдена пара ключ=значение
     * соответствующая данным из аннотации {@link ru.sbermarket.common.config.Config} значение для этой переменной задается из указанного
     * дефолтного значение {@link ru.sbermarket.common.config.Config#defaultValue()}
     * В случае если файл не был найден, выпадает {@link Exception}
     * В случае если не удалось получить значение из файла, бросается {@link NumberFormatException}
     * В качестве аргументов получает:
     *
     * @param configClass - класс в котором ищутся поля аннотированные {@link ru.sbermarket.common.config.Config}
     * @param configName  - название конфигурационного файла
     */
    public void loadConfig(final Class<?> configClass, String configName, String configDir) {
        var env = configClass.getAnnotation(Env.class);
        if (nonNull(env)) {
            var envProperty = System.getProperty("env", configName);
            var path = envProperty.split("-");
            if (path.length == 2) {
                configDir += "/" + path[0];
                configName = path[1];
            }
        }
        var filePath = configDir + "/" + configName + ".properties";
        try (var lnr = new LineNumberReader(new InputStreamReader(FileUtils.getConfig(filePath, configClass), StandardCharsets.UTF_8))) {
            PROPERTIES.load(lnr);

            for (final var field : configClass.getDeclaredFields()) {
                Config annotation;
                if (nonNull((annotation = field.getAnnotation(Config.class)))) {
                    field.setAccessible(true);

                    final var fieldName = annotation.fieldName();
                    final var type = field.getType().getSimpleName().toLowerCase();
                    final var isEncrypted = annotation.encrypted();
                    final var args = System.getProperty(annotation.args(), "");
                    var defaultValue = annotation.defaultValue();

                    if (isEncrypted && (nonNull(defaultValue) && !defaultValue.isEmpty())) {
                        defaultValue = Crypt.INSTANCE.decrypt(defaultValue);
                    }

                    try {
                        switch (type) {
                            case "boolean":
                                final var defaultBool = isEmpty(defaultValue) ? "false" : defaultValue;
                                field.setBoolean(null, PROPERTIES.getBooleanProperty(fieldName, defaultBool, args));
                                break;
                            case "byte":
                                final var defaultByte = isEmpty(defaultValue) ? "0" : defaultValue;
                                field.setByte(null, PROPERTIES.getByteProperty(fieldName, defaultByte, args));
                                break;
                            case "int":
                                final var defaultInt = isEmpty(defaultValue) ? "0" : defaultValue;
                                field.setInt(null, PROPERTIES.getIntProperty(fieldName, defaultInt, args));
                                break;
                            case "long":
                                final var defaultLong = isEmpty(defaultValue) ? "0" : defaultValue;
                                field.setLong(null, PROPERTIES.getLongProperty(fieldName, defaultLong, args));
                                break;
                            case "float":
                                final var defaultFloat = isEmpty(defaultValue) ? "0.0" : defaultValue;
                                field.setFloat(null, PROPERTIES.getFloatProperty(fieldName, defaultFloat, args));
                                break;
                            case "list":
                                final var defaultList = isEmpty(defaultValue) ? "empty" : defaultValue;
                                field.set(null, PROPERTIES.getListProperty(fieldName, defaultList, args));
                                break;
                            case "double":
                                final var defaultDouble = isEmpty(defaultValue) ? "0.0" : defaultValue;
                                field.setDouble(null, PROPERTIES.getDoubleProperty(fieldName, defaultDouble, args));
                                break;
                            case "string":
                                final var value = PROPERTIES.getStringProperty(fieldName, defaultValue, args);
                                field.set(null, (isEncrypted && notEmpty(value)) ? Crypt.INSTANCE.decrypt(value) : value);
                                break;
                            default:
                                log.debug("Unknown field type: " + field.getType().getSimpleName() + " field name: " + field.getName() + " config: " + configName + ".properties");
                                break;
                        }
                    } catch (NumberFormatException e) {
                        log.error("Failed to Load " + filePath + " file. Field: " + field.getName() + " " + e.getMessage());
                    }
                    log.debug(configName + ": set " + field.getName() + "{" + fieldName + "} = " + (isEncrypted ? "**********" : field.get(null)));
                }
            }
        } catch (Exception e) {
            log.error("Failed to Load " + filePath + " file.", e);
        }
    }

    private static boolean isEmpty(final String value) {
        return isNull(value) || value.isEmpty() || value.isBlank();
    }

    private static boolean notEmpty(final String value) {
        return nonNull(value) && !value.isEmpty() && !value.isBlank();
    }
}
