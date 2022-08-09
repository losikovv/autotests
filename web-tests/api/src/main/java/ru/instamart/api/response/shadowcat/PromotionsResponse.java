package ru.instamart.api.response.shadowcat;

import lombok.*;
import ru.instamart.api.model.shadowcat.Promotion;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class PromotionsResponse extends BaseResponseObject {
    private int count;
    private List<Promotion> items;
}

