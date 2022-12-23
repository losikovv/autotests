package ru.instamart.api.enums;

import lombok.AllArgsConstructor;

import static java.lang.String.format;

public interface BashCommands {
    @AllArgsConstructor
    enum Instacoins implements BashCommands {
        /**
         * Добавление инстакоинов через консоль для пользователя
         * передаются: email, instacoin, userId
         */
        ADD_USER_INSTACOIN("/vault/vault-env bundle exec rake \"instacoin:add_for_contest[%s, %s, %s,'proverochka']\"");
        private String command;
        public String get(String... values) {
            return format(command, (Object[]) values);
        }
    }

    @AllArgsConstructor
    enum ShipmentDelays implements BashCommands {
        /**
         * Обновление информации о задержках доставки
         */
        COMPUTE_EXPECTED_DATES("/vault/vault-env bundle exec rake shipment_delays:compute_expected_dates"),
        SEND_NOTIFICATIONS("/vault/vault-env bundle exec rake shipment_delays:send_notifications");
        private String command;
        public String get() {
            return command;
        }
    }

    @AllArgsConstructor
    enum Promotions implements BashCommands {
        /**
         * Создание промо для компенсаций
         */
        CREATE_COMPENSATION_PROMOTIONS("/vault/vault-env bundle exec rake promotions:create_compensation_promotions");
        private String command;
        public String get() {
            return command;
        }
    }

    @AllArgsConstructor
    enum ServiceEnvironmentProperties implements BashCommands {
        /**
         * Получение переменных окружения сервисов из swissknife
         */
        ETA_ENABLE_STORE_ON_DEMAND_CHECK("cat /proc/1/environ | tr '\\0' '\\n' | grep -i ETA_ENABLE_STORE_ON_DEMAND_CHECK"),
        ETA_SURGE_INTERVALS("cat /proc/1/environ | tr '\\0' '\\n' | grep -i SURGE_INTERVALS"),
        SHIPPINGCALC_SURGE_DISABLED("cat /proc/1/environ | tr '\\0' '\\n' | grep -i SURGE_DISABLED"),
        SHIPPINGCALC_PLANNED_SURGE_FEATURE("cat /proc/1/environ | tr '\\0' '\\n' | grep -i PLANNED_SURGE_FEATURE_FLAG"),
        SURGE_EVENT_OUTDATE("cat /proc/1/environ | tr '\\0' '\\n' | grep -i SURGEEVENT_OUTDATE"),
        SURGE_HTTP_AUTH_TOKENS("cat /proc/1/environ | tr '\\0' '\\n' | grep -i HTTP_AUTH_TOKENS");
        private String command;
        public String get() {
            return command;
        }
    }
}
