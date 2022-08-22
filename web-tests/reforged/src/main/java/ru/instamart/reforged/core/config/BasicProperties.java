package ru.instamart.reforged.core.config;

import ru.sbermarket.common.config.Config;

public final class BasicProperties {

    public static final String NAME = "basic";

    @Config(configName = NAME, fieldName = "sberIdUrl", defaultValue = "https://online.sberbank.ru/")
    public static String SBER_ID_URL;
    @Config(configName = NAME, fieldName = "jobLandingUrl", defaultValue = "https://job.sbermarket.ru/")
    public static String JOB_LANDING_URL;
    @Config(configName = NAME, fieldName = "rbsuatPaymentsUrl", defaultValue = "https://web.rbsuat.com/")
    public static String RBSUAT_PAYMENTS_URL;
    @Config(configName = NAME, fieldName = "demoCloudPaymentsUrl", defaultValue = "https://demo.cloudpayments.ru/")
    public static String DEMO_CLOUD_PAYMENTS_URL;
}
