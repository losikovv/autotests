package ru.instamart.api.enums;

import lombok.AllArgsConstructor;

import static java.lang.String.format;

public interface BashCommands {
    @AllArgsConstructor
    enum Instacoins implements BashCommands{
        /**
         * Добавление инстакоинов через консоль для пользователя
         * передаются: email, instacoin, userId
         */
        ADD_USER_INSATCOIN("/vault/vault-env bundle exec rake \"instacoin:add_for_contest[%s, %s, %s,'proverochka']\"");
        private String command;
        public String get(String... values) {
            return format(command, (Object[]) values);
        }
    }
}
