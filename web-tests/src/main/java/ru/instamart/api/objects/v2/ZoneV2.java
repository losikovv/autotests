package ru.instamart.api.objects.v2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.instamart.api.objects.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
public class ZoneV2 extends BaseObject {
    private Double lon;
    private Double lat;

    @Override
    public String toString() {
        return "lat: " + lat + ", lon: " + lon;
    }
}
