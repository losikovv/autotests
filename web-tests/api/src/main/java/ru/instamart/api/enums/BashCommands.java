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
}
