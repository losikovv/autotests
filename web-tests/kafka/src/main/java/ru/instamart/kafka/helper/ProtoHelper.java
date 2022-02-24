package ru.instamart.kafka.helper;

import com.google.protobuf.Message;
import com.google.protobuf.TextFormat;
import lombok.extern.slf4j.Slf4j;
import ru.instamart.kafka.log_model.Response;
import ru.sbermarket.common.Mapper;

import java.util.Objects;

@Slf4j
public class ProtoHelper {

    public static  <T extends Message.Builder> T parseResponseToProto(final String item, final T builder) {
        try {
            String response = Mapper.INSTANCE.jsonToObject(Objects.requireNonNull(item), Response.class).getResponse();
            if (Objects.nonNull(response)) {
                com.google.protobuf.TextFormat.getParser().merge(response, builder);
                return builder;
            }
        } catch (TextFormat.ParseException e) {
            log.info("Ошибка обработки response: {}", e.getMessage());
        }
        return builder;
    }
}
