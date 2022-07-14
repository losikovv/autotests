package ru.instamart.reforged.core.data_provider;

import ru.instamart.reforged.core.config.UiProperties;
import ru.instamart.reforged.core.service.CurlCommandBuilder;

public class CurlCommand {

    public static String getStatusCodeSelgros() {
        return CurlCommandBuilder.getStatusCode(UiProperties.HEADER_SELGROS_FORWARD_TO);
    }

    public static String getStatusCodeSbermarket() {
         return CurlCommandBuilder.getStatusCode(UiProperties.HEADER_STF_FORWARD_TO);
    }
}
