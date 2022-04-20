package ru.instamart.jdbc.entity.candidates;

import lombok.Data;

@Data
public class CandidatesEntity {
    private Long id;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;
    private String uuid;
    private String fullName;
    private String transport;
    private Boolean active;
    private String sentStatusAt;
}
