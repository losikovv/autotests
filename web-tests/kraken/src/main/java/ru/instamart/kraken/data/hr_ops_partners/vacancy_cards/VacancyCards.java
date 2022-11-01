package ru.instamart.kraken.data.hr_ops_partners.vacancy_cards;

import java.util.Set;

public class VacancyCards {

    public static VacancyCardData collectorSPB() {
        return VacancyCardData.builder()
                .pageUrl("/sborshchik-zakazov")
                .homePageTitle("Cборщик заказов в СберМаркет")
                .shortDescription("Собирайте продукты в магазине и передавайте курьерам")
                .homePageSalary("Доход до 47 000 рублей в месяц и бонусы")
                .actionButtonText("Стать сборщиком")
                .homePageAdvantages(
                        Set.of(
                                new AdvantageData("Район на ваш выбор", "Рядом с домом или в центре города"),
                                new AdvantageData("Работа с комфортом", "Удобная брендированная форма"),
                                new AdvantageData("Бонусы", "Безлимитный интернет и мобильная связь со скидкой\n" +
                                        "Скидки на питание\n" +
                                        "Скидки на онлайн сервисы от партнёров СберМаркет")))
                .anotherVacancyCardOnVacancyPageTitle("Сборщиком заказов")
                .anotherVacancyCardOnVacancyPageDescription("Собирайте продукты в магазине и передавайте курьерам")
                .build();
    }

    public static VacancyCardData collectorMSK() {
        return VacancyCardData.builder()
                .pageUrl("/sborshchik-zakazov")
                .homePageTitle("Cборщик заказов в СберМаркет")
                .vacancyPageTitle("РАБОТАЙТЕ СБОРЩИКОМ ЗАКАЗОВ В МОСКВЕ")
                .shortDescription("Собирайте продукты в магазине и передавайте курьерам")
                .fullDescription("Cборщик выбирает лучшие товары в магазине, внимательно проверяет качество и срок годности продуктов," +
                        " оплачивает их на кассе, упаковывает и передаёт курьеру. Иногда созванивается с клиентом и договаривается о замене товаров.")
                .homePageSalary("Доход до 57 000 рублей в месяц и бонусы")
                .actionButtonText("Стать сборщиком")
                .homePageAdvantages(
                        Set.of(
                                new AdvantageData("Район на ваш выбор", "Рядом с домом или в центре города"),
                                new AdvantageData("Работа с комфортом", "Удобная брендированная форма"),
                                new AdvantageData("Бонусы", "Безлимитный интернет и мобильная связь со скидкой\n" +
                                        "Скидки на питание\n" +
                                        "Скидки на онлайн сервисы от партнёров СберМаркет")))
                .vacancyPageAdvantages(
                        Set.of(
                                new AdvantageData("Доход до 57 000 ₽ в месяц", "Выплаты еженедельно"),
                                new AdvantageData("Район на ваш выбор", "Рядом с домом или в центре города"),
                                new AdvantageData("Бонусы", "Безлимитный интернет и мобильная связь со скидкой\n" +
                                        "Скидки на питание\n" +
                                        "Скидки на онлайн сервисы от партнёров СберМаркет"),
                                new AdvantageData("Работа с комфортом", "Удобная брендированная форма")))
                .anotherVacancyCardOnVacancyPageTitle("Сборщиком заказов")
                .anotherVacancyCardOnVacancyPageDescription("Собирайте продукты в магазине и передавайте курьерам")
                .build();
    }

    public static VacancyCardData collectorCourierSPB() {
        return VacancyCardData.builder()
                .pageUrl("/sborshchik-kurer")
                .homePageTitle("Cборщик-курьер в СберМаркет")
                .shortDescription("Собирайте продукты в магазине и доставляйте клиентам")
                .homePageSalary("Доход до 64 000 рублей в месяц и бонусы")
                .actionButtonText("Стать сборщиком-курьером")
                .homePageAdvantages(
                        Set.of(
                                new AdvantageData("Район на ваш выбор", "Рядом с домом или в центре города"),
                                new AdvantageData("Работа с комфортом", "Удобная брендированная форма"),
                                new AdvantageData("Бонусы", "Cкидки на покупку и аренду велосипеда\n" +
                                        "Безлимитный интернет и мобильная связь со скидкой\n" +
                                        "Скидки на питание\n" +
                                        "Скидки на онлайн сервисы от партнёров СберМаркет")))
                .anotherVacancyCardOnVacancyPageTitle("Сборщиком-курьером")
                .anotherVacancyCardOnVacancyPageDescription("Собирайте продукты в магазине и доставляйте клиентам")
                .build();
    }

    public static VacancyCardData collectorCourierMSK() {
        return VacancyCardData.builder()
                .pageUrl("/sborshchik-kurer")
                .homePageTitle("Cборщик-курьер в СберМаркет")
                .vacancyPageTitle("РАБОТАЙТЕ СБОРЩИКОМ-КУРЬЕРОМ В МОСКВЕ")
                .shortDescription("Собирайте продукты в магазине и доставляйте клиентам")
                .fullDescription("Сборщик-курьер работает в нескольких магазинах в шаговой доступности. Он выбирает лучшие товары, " +
                        "внимательно проверяет качество и срок годности продуктов, оплачивает их на кассе, упаковывает и доставляет клиентам.")
                .homePageSalary("Доход до 94 000 рублей в месяц и бонусы")
                .actionButtonText("Стать сборщиком-курьером")
                .homePageAdvantages(
                        Set.of(
                                new AdvantageData("Район на ваш выбор", "Рядом с домом или в центре города"),
                                new AdvantageData("Работа с комфортом", "Удобная брендированная форма"),
                                new AdvantageData("Бонусы", "Cкидки на покупку и аренду велосипеда\n" +
                                        "Безлимитный интернет и мобильная связь со скидкой\n" +
                                        "Скидки на питание\n" +
                                        "Скидки на онлайн сервисы от партнёров СберМаркет")))
                .vacancyPageAdvantages(
                        Set.of(
                                new AdvantageData("Доход до 94 000 ₽ в месяц", "Получай выплаты каждую неделю"),
                                new AdvantageData("Район на ваш выбор", "Рядом с домом или в центре города"),
                                new AdvantageData("Бонусы", "Скидки на покупку и аренду велосипеда\n" +
                                        "Безлимитный интернет и мобильная связь со скидкой\n" +
                                        "Скидки на питание\n" +
                                        "Скидки на онлайн сервисы от партнёров СберМаркет"),
                                new AdvantageData("Работа с комфортом", "Удобная брендированная форма")))
                .anotherVacancyCardOnVacancyPageTitle("Сборщиком-курьером")
                .anotherVacancyCardOnVacancyPageDescription("Собирайте продукты в магазине и доставляйте клиентам")
                .build();
    }

    public static VacancyCardData collectorCourierAutoSPB() {
        return VacancyCardData.builder()
                .pageUrl("/sborshchik-kurer-avto")
                .homePageTitle("Cборщик-курьер на авто в СберМаркет")
                .shortDescription("Находите в магазине товары по списку и доставляйте их клиентам на личном автомобиле")
                .homePageSalary("Доход до 105 792 рублей в месяц и бонусы")
                .actionButtonText("Стать сборщиком-курьером на авто")
                .homePageAdvantages(
                        Set.of(
                                new AdvantageData("Техобслуживание", "Обслуживание авто у нашего партнёра — FitService"),
                                new AdvantageData("Работа с комфортом", "Удобная брендированная форма"),
                                new AdvantageData("Бонусы", "Безлимитный интернет и мобильная связь со скидкой\n" +
                                        "Скидки на питание\n" +
                                        "Скидки на онлайн сервисы от партнёров СберМаркет")))
                .anotherVacancyCardOnVacancyPageTitle("Сборщиком-курьером на авто")
                .anotherVacancyCardOnVacancyPageDescription("Находите в магазине товары по списку и доставляйте их клиентам на личном автомобиле")
                .build();
    }

    public static VacancyCardData collectorCourierAutoMSK() {
        return VacancyCardData.builder()
                .pageUrl("/sborshchik-kurer-avto")
                .homePageTitle("Cборщик-курьер на авто в СберМаркет")
                .vacancyPageTitle("СТАНЬ СБОРЩИКОМ-КУРЬЕРОМ НА АВТО В МОСКВЕ")
                .shortDescription("Находите в магазине товары по списку и доставляйте их клиентам на личном автомобиле")
                .fullDescription("Сборщик-курьер на автомобиле работает в нескольких магазинах в радиусе 3 километров. " +
                        "Собирает лучшие товары по списку и доставляет заказ клиенту на своём автомобиле.")
                .homePageSalary("Доход до 105 792 рублей в месяц и бонусы")
                .actionButtonText("Стать сборщиком-курьером на авто")
                .homePageAdvantages(
                        Set.of(
                                new AdvantageData("Техобслуживание", "Обслуживание авто у нашего партнёра — FitService"),
                                new AdvantageData("Работа с комфортом", "Удобная брендированная форма"),
                                new AdvantageData("Бонусы", "Безлимитный интернет и мобильная связь со скидкой\n" +
                                        "Скидки на питание\n" +
                                        "Скидки на онлайн сервисы от партнёров СберМаркет")))
                .vacancyPageAdvantages(
                        Set.of(
                                new AdvantageData("Доход до 105 792 ₽ в месяц", "Выплаты еженедельно"),
                                new AdvantageData("Техобслуживание", "Обслуживание авто у нашего партнёра — FitService"),
                                new AdvantageData("Работа с комфортом", "Удобная брендированная форма"),
                                new AdvantageData("Бонусы", "Безлимитный интернет и мобильная связь со скидкой\n" +
                                        "Скидки на питание\n" +
                                        "Скидки на онлайн сервисы от партнёров СберМаркет")))
                .anotherVacancyCardOnVacancyPageTitle("Сборщиком-курьером на авто")
                .anotherVacancyCardOnVacancyPageDescription("Находите в магазине товары по списку и доставляйте их клиентам на личном автомобиле")
                .build();
    }

    public static VacancyCardData driverCourierSPB() {
        return VacancyCardData.builder()
                .pageUrl("/voditel-kurer")
                .homePageTitle("Водитель-курьер в СберМаркет")
                .shortDescription("Доставляйте заказы клиентам")
                .homePageSalary("Доход до 115 000 рублей в месяц и бонусы")
                .actionButtonText("Стать водителем-курьером")
                .homePageAdvantages(
                        Set.of(
                                new AdvantageData("Техобслуживание", "Обслуживание авто у нашего партнёра — FitService"),
                                new AdvantageData("Работа с комфортом", "Удобная брендированная форма"),
                                new AdvantageData("Бонусы", "Безлимитный интернет и мобильная связь со скидкой\n" +
                                        "Скидки на питание\n" +
                                        "Скидки на онлайн сервисы от партнёров СберМаркет")))
                .anotherVacancyCardOnVacancyPageTitle("Водителем-курьером")
                .anotherVacancyCardOnVacancyPageDescription("Доставляйте заказы клиентам")
                .build();
    }

    public static VacancyCardData driverCourierMSK() {
        return VacancyCardData.builder()
                .pageUrl("/voditel-kurer")
                .homePageTitle("Водитель-курьер в СберМаркет")
                .vacancyPageTitle("РАБОТАЙТЕ ВОДИТЕЛЕМ-КУРЬЕРОМ В МОСКВЕ")
                .shortDescription("Доставляйте заказы клиентам")
                .fullDescription("Водитель-курьер забирает собранные заказы из магазина и отвозит клиентам, вручает их лично или бесконтактно. Работа на своём автомобиле.")
                .homePageSalary("Доход до 127 000 рублей в месяц и бонусы")
                .actionButtonText("Стать водителем-курьером")
                .homePageAdvantages(
                        Set.of(
                                new AdvantageData("Техобслуживание", "Обслуживание авто у нашего партнёра — FitService"),
                                new AdvantageData("Работа с комфортом", "Удобная брендированная форма"),
                                new AdvantageData("Бонусы", "Безлимитный интернет и мобильная связь со скидкой\n" +
                                        "Скидки на питание\n" +
                                        "Скидки на онлайн сервисы от партнёров СберМаркет")))
                .vacancyPageAdvantages(
                        Set.of(
                                new AdvantageData("Доход до 127 000 ₽ в месяц", "Получай выплаты каждую неделю"),
                                new AdvantageData("Техобслуживание", "Обслуживание авто у нашего партнёра — FitService"),
                                new AdvantageData("Работа с комфортом", "Удобная брендированная форма"),
                                new AdvantageData("Бонусы", "Безлимитный интернет и мобильная связь со скидкой\n" +
                                        "Скидки на питание\n" +
                                        "Скидки на онлайн сервисы от партнёров СберМаркет")))
                .anotherVacancyCardOnVacancyPageTitle("Водителем-курьером")
                .anotherVacancyCardOnVacancyPageDescription("Доставляйте заказы клиентам")
                .build();
    }

    public static VacancyCardData callCenterOperatorSPB() {
        return VacancyCardData.builder()
                .pageUrl("/operator-kontaktnogo-centra")
                .homePageTitle("Оператор контактного центра в СберМаркет")
                .shortDescription("Принимайте звонки и общайтесь с клиентами СберМаркета")
                .homePageSalary("Доход до 30 000 рублей в месяц и бонусы")
                .vacancyPageSalary("Зарабатывайте до 30 000 рублей в месяц")
                .actionButtonText("Стать оператором")
                .homePageAdvantages(
                        Set.of(
                                new AdvantageData("Стабильность", "Мы быстрорастущая компания в экосистеме Сбера"),
                                new AdvantageData("Социальный пакет", "25 000 ₽ в год, которые можно потратить на ДМС, обучение и билеты на отдых"),
                                new AdvantageData("Официальное трудоустройство", "Оформляем по ТК РФ с первого дня работы")))
                .build();
    }

    public static VacancyCardData callCenterOperatorMSK() {
        return VacancyCardData.builder()
                .pageUrl("/operator-kontaktnogo-centra")
                .homePageTitle("Оператор контактного центра в СберМаркет")
                .vacancyPageTitle("РАБОТАЙТЕ ОПЕРАТОРОМ КОНТАКТНОГО ЦЕНТРА УДАЛЕННО")
                .shortDescription("Принимайте звонки и общайтесь с клиентами СберМаркета")
                .fullDescription("Оператор контактного центра принимает звонки клиентов СберМаркета, вежливо и с пониманием " +
                        "разбирается в сути обращений и решает любой вопрос. Иногда оператор звонит клиентам сам для повышения качества сервиса.")
                .homePageSalary("Доход до 30 000 рублей в месяц и бонусы")
                .vacancyPageSalary("Зарабатывайте до 30 000 рублей в месяц")
                .actionButtonText("Стать оператором")
                .homePageAdvantages(
                        Set.of(
                                new AdvantageData("Стабильность", "Мы быстрорастущая компания в экосистеме Сбера"),
                                new AdvantageData("Социальный пакет", "25 000 ₽ в год, которые можно потратить на ДМС, обучение и билеты на отдых"),
                                new AdvantageData("Официальное трудоустройство", "Оформляем по ТК РФ с первого дня работы")))
                .vacancyPageAdvantages(
                        Set.of(
                                new AdvantageData("Быстрое обучение", "Опытный наставник научит всему, что умеет сам. Мы оплатим время, которое уйдёт на обучение"),
                                new AdvantageData("Официальное трудоустройство", "Оформляем по ТК РФ с первого дня работы"),
                                new AdvantageData("Социальный пакет", "25 000 ₽ в год, которые можно потратить на ДМС, обучение и билеты на отдых"),
                                new AdvantageData("Стабильность", "Мы быстрорастущая компания в экосистеме Сбера")))
                .build();
    }

    public static VacancyCardData callCenterOperatorOrel() {
        return VacancyCardData.builder()
                .vacancyPageTitle("РАБОТАЙТЕ ОПЕРАТОРОМ КОНТАКТНОГО ЦЕНТРА В ОРЛЕ")
                .build();
    }

    public static VacancyCardData callCenterOperatorTomsk() {
        return VacancyCardData.builder()
                .vacancyPageTitle("РАБОТАЙТЕ ОПЕРАТОРОМ КОНТАКТНОГО ЦЕНТРА В ТОМСКЕ")
                .build();
    }
}
