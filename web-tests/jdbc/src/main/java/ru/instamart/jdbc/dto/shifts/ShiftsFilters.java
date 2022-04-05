package ru.instamart.jdbc.dto.shifts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ShiftsFilters {
    private int limit;
    private int offset;
    private Integer id;
}
