package ru.instamart.reforged.core.service;

import ru.instamart.kraken.config.EnvironmentProperties;

public class CurlCommandBuilder {

    // -s скрывает прогресс
    // -o записать овтет в
    // --head получить только заголовок
    // -w вывести только
    // -H передать заголовки
    public static String getStatusCode(String header) {
        return "curl -s -o --head" + (EnvironmentProperties.Env.isPreprod()
                ? (" -H sbm-forward-feature-version-stf:"
                + header) : "")
                + " -w \"%{http_code}\" ";
    }
}
