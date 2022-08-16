package ru.instamart.grpc.helper;

import com.google.protobuf.Message;
import com.google.protobuf.TextFormat;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class ProtoHelper {

    public static  <T extends Message.Builder> T parseResponseToProto(final byte[] msg, final T builder) {
        try {
            if (Objects.nonNull(msg)) {
                com.google.protobuf.TextFormat.getParser().merge(msg.toString(), builder);
                return builder;
            }
        } catch (TextFormat.ParseException e) {
            log.info("Ошибка обработки response: {}", e.getMessage());
        }
        return builder;
    }
}
