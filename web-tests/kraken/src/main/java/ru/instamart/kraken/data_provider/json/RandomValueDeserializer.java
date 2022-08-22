package ru.instamart.kraken.data_provider.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.IOException;

/**
 * Для того что бы вернуть рандомное значение поля нужно в json указать следующий токен
 * обязательный префикс RND_
 * с возможными вариантами рандоманизации
 * для строки это будет STR
 * для цифрового значения NUM
 * для цифро-буквенного значения STRNUM
 * обязателен постфикс _1 который обозначает необходимое рандомное количество букв или цифр
 * итоговый вариант для одной рандомной буквы RND_STR_1
 * итоговый вариант для одной рандомной цифры RND_NUM_1
 *
 */
public final class RandomValueDeserializer extends JsonDeserializer<String> {

    @Override
    public String deserialize(JsonParser p, DeserializationContext context) throws IOException {
        var value= p.getValueAsString();
        if (value.contains("RND")) {
            var rnd = value.split("_");
            var type = Rnd.valueOf(rnd[1]);
            var count = Integer.parseInt(rnd[2]);

            switch (type) {
                case STR:
                    return RandomStringUtils.randomAlphabetic(count);
                case NUM:
                    return RandomStringUtils.randomNumeric(count);
                case STRNUM:
                    return RandomStringUtils.randomAlphanumeric(count);
            }
        }

        return value;
    }

    private enum Rnd {
        STR,
        NUM,
        STRNUM
    }
}
