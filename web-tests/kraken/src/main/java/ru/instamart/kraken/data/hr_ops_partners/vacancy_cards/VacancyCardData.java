package ru.instamart.kraken.data.hr_ops_partners.vacancy_cards;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public final class VacancyCardData {

    private final String pageUrl;
    private final String homePageTitle;
    private final String vacancyPageTitle;
    private final String shortDescription;
    private final String fullDescription;
    private final String homePageSalary;
    private final String vacancyPageSalary;
    private final String actionButtonText;
    private final Set<AdvantageData> homePageAdvantages;
    private final Set<AdvantageData> vacancyPageAdvantages;
    private final String anotherVacancyCardOnVacancyPageTitle;
    private final String anotherVacancyCardOnVacancyPageDescription;
}
