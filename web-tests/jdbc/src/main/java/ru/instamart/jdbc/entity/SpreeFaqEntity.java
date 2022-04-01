package ru.instamart.jdbc.entity;

import lombok.Data;

@Data
public class SpreeFaqEntity {
    private Long id;
    private Long faqGroupId;
    private Integer position;
    private String question;
    private String answer;
    private String createdAt;
    private String updatedAt;
}
