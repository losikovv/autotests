package ru.instamart.api.objects.v2;

import ru.instamart.api.objects.BaseObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
public class Zone extends BaseObject {
    private Double lon;
    private Double lat;

    @Override
    public String toString() {
        return "lat: " + lat + ", lon: " + lon;
    }
}
