package ru.instamart.kraken.data.hr_ops_partners.faq;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
public final class FaqData {
    private final String question;
    private final String answer;
}
