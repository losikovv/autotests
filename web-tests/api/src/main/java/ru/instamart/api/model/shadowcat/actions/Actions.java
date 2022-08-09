package ru.instamart.api.model.shadowcat.actions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.instamart.api.model.BaseObject;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class Actions extends BaseObject {
    @Singular("act") private List<Action> list;

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class Action extends BaseObject{
        @Singular("method") private List<String> methods;
        private Properties properties;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class Properties extends BaseObject{
        @JsonProperty(value = "max_fix_amount")
        private int maxFixAmount;
        private int percent;
    }
}