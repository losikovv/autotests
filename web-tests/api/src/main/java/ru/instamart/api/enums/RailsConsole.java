package ru.instamart.api.enums;

import lombok.AllArgsConstructor;

import static java.lang.String.format;


public interface RailsConsole {

    @AllArgsConstructor
    enum Order implements RailsConsole {
        START_COLLECTING("Spree::Shipment.find_by_number('%s').start_collecting!"),
        START_SHIPPING("Spree::Shipment.find_by_number('%s').start_shipping!"),
        SHIP("Spree::Shipment.find_by_number('%s').ship!"),
        CANCEL("Spree::Order.find_by_number('%s').cancel!"),
        STOP_COLLECTING("Spree::Shipment.find_by_number('%s').stop_collecting!"),
        FINISH_COLLECTING("Spree::Shipment.find_by_number('%s').finish_collecting!"),
        STOP_SHIPPING("Spree::Shipment.find_by_number('%s').stop_shipping!"),
        FIND_BY_NUMBER("Spree::Shipment.find_by_number('%s')"),
        ASSEMBLY_ITEMS_ORDER("Spree::Shipment.find_by_number('%s').line_items[%s].update_attributes(assembly_issue: 'Собрано', assembled: true)"),
        CANCEL_ITEMS_ORDER("ShopperCart.new(Spree::Shipment.find_by_number('%s')).cancel_item(Spree::Shipment.find_by_number('%s').line_items[%s], reason: 'Нет в наличии')");

        private String command;

        public String get(String... values) {
            return format(command, (Object[]) values);
        }
    }

    @AllArgsConstructor
    enum Other implements RailsConsole {

        /**
         * удаление всех фильтров категорий
         * без параметров
         */
        DESTROY_ALL("Spree::ProductFilterGroup.includes(:filters).destroy_all"),
        /**
         * Получение всех фильтров категорий
         * без параметров
         */
        COUNT("Spree::ProductFilter.count"),
        /**
         * отправить событие metro promo в customer.io (у клиента есть любимые товары, по которым есть скидка)
         * без параметров
         */
        SEND_EVENT("CustomerIo::DiscountsMailerJob.new.perform"),
        /**
         * задать время оформления (завершения) заказа
         * get(Номер заказа)
         */
        SET_TIME_FOR_REGISTRATION("Spree::Order.find_by(number: '%s').update_column(:completed_at, Time.current - 7.days)"),
        /**
         * задать время доставки заказа
         * get(Номер заказа)
         */
        SET_ORDER_DELIVERY_TIME("Spree::Shipment.find_by(number: '%s').update_column(:shipped_at, Time.current - 7.days)"),
        /**
         * вывести время оформления (завершения) заказа
         * get(Номер заказа)
         */
        DISPLAY_TIME_OF_ORDERING("Spree::Order.find_by(number: '%s').completed_at"),
        /**
         * вывести время доставки заказа (не выводится в админке)
         * get(Номер заказа)
         */
        DISPLAY_TIME_OF_ORDER_DELIVERY("Spree::Order.find_by(number: '%s').shipped_at"),
        /**
         * добавление новой роли
         * get(Номер заказа)
         */
        ADDING_NEW_ROLE("Spree::Role.create(name: '%s')"),
        /**
         * последняя автризация в СберПей
         * без параметров
         */
        SBER_PAYMENT_AUTHORIZATION("SberPaymentAuthorization.last"),
        /**
         * все методы оплаты
         * без параметров
         */
        PAYMENT_METHOD("Spree::PaymentMethod.all"),
        /**
         * поиск X-Spree-Token
         * Spree::RolesUser.find_by(role_id: 1).user.spree_api_key (role_id: 1 - это admin (посмотреть можно так, например: Spree::Role.all))
         * get(role_id)
         */
        X_SPREE_TOKEN("Spree::RolesUser.find_by(role_id: %s).user.spree_api_key"),
        /**
         * #Получить id магазина по uuid из адресной строки в админке
         * get(UUID)
         */
        SID_FIND_BY_UUID("Store.find_by(uuid: '%s')"),
        /**
         * Очистить кэш compensation_promotions
         * без параметров
         */
        DELETE_COMPENSATIONS_CACHE("Rails.cache.delete(:compensation_promotions)"),
        /**
         * Удалить последний shipment_return
         * без параметров
         */
        DELETE_SHIPMENT_RETURN("ShipmentReturn.last.delete");


        private String command;

        public String get() {
            return command;
        }

        public String get(String value) {
            return format(command, value);
        }
    }

    @AllArgsConstructor
    enum User implements RailsConsole {
        FIND_BY_PHONE("Spree::User.find_by_sql('SELECT * FROM spree_users INNER JOIN phone_tokens ON spree_users.id = phone_tokens.user_id WHERE phone_tokens.value=%s').last().id"),
        ADD_ROLE("Spree::User.find(%s).roles << Spree::Role.find_by_name('%s')"),
        ADD_ALL_ROLES("Spree::User.find(%s).roles = Spree::Role.all"),
        CREATE_ADMIN("Spree::User.create(first_name: '%s', last_name: '%s', email: '%s', password: '%s', spree_api_key: SecureRandom.hex, roles: Spree::Role.all)");


        private String command;

        public String get(String... values) {
            return format(command, (Object[]) values);
        }
    }

    @AllArgsConstructor
    enum ExternalPartners implements RailsConsole {
        SUBSCRIPTION("ExternalPartners::Subscription.create(service_id: 1, user_id: Spree::User.find_by_email('%s').id, expired_date: 10.years.from_now, client_key_type: 'SUB', client_key: '1', begins_date: 1.day.ago, external_id: SecureRandom.uuid, packet_id: '1')");

        private String command;

        public String get(String... values) {
            return format(command, (Object[]) values);
        }
    }


    @AllArgsConstructor
    enum ApiV3 implements RailsConsole {
        GET_RETAILER("Spree::Retailer.where(slug: :%s)"),
        OFFER_WHERE_LAST("Offer.where(%s).last"),
        OFFER_JOIN_PRODUCT("Offer.joins(:product).where(%s).last"),
        CREATE_API_CLIENT("ApiClient.create(client_id: '%s', verifiable: false, custom_prices: true, tenant_id: '%s', custom_promo: false,  sku_kind: 'sku_kind_internal', basic_auth: nil, webhook_auth_token: nil, card_payment_method: nil, notifiable_by_sms: true,  notifiable_by_email: true).tokens.create(value: SecureRandom.base64(64))");
        private String command;

        public String get(String... values) {
            return format(command, (Object[]) values);
        }
    }

    @AllArgsConstructor
    enum Flipper implements RailsConsole {
        ALLOW_EXPORT_TO_EXTERNAL_SERVICES("Flipper[:allow_export_to_external_services].%s"),
        EXPORT_TO_EXTERNAL_SERVICES_BY_WEBHOOK("Flipper[:export_to_external_services_by_webhook].%s"),
        NEW_ADMIN_ROLES("Flipper[:new_admin_roles].%s");

        private String command;

        public String get(String... values) {
            return format(command, (Object[]) values);
        }
    }

    @AllArgsConstructor
    enum Shipments implements RailsConsole {
        UPDATE_SHIPMENT_INDEX_BY_PAYMENT_STATE("Spree::Admin::ShipmentsFinder.new({search: {payment_state: ['%s']}}).find_shipments.each(&:index)\n" +
                "Sunspot.session.commit");

        private String command;

        public String get(String... values) {
            return format(command, (Object[]) values);
        }
    }

    @AllArgsConstructor
    enum ApiClient implements RailsConsole {
        FIND_OR_CREATE_QA_SERVICE("ApiClient.find_or_create_by(client_id: ApiClient::SBER_APP_ID, tenant_id: 'sbermarket')");

        private String command;

        public String get(String... values) {
            return format(command, (Object[]) values);
        }
    }

    @AllArgsConstructor
    enum WebhookClient implements RailsConsole {
        SEND_MESSAGE("Api::V3::WebhookClient.new(ApiClient.find_by(client_id: '%s')).delivery_windows_updated('%s')"),
        ADD_WEBHOOK_URL("ApiClientHook.kinds.each { |(kind, i)| (ApiClient.find_by(client_id: '%s')).api_client_hooks.create!(kind: i, url: '%s') }");

        private String command;

        public String get(String... values) {
            return format(command, (Object[]) values);
        }
    }
}
