package instamart.api.v2.objects;

import instamart.api.common.BaseObject;

public class Zone extends BaseObject {

    private Double lon;
    private Double lat;

    /**
     * No args constructor for use in serialization
     *
     */
    public Zone() {
    }

    /**
     *
     * @param lon
     * @param lat
     */
    public Zone(Double lon, Double lat) {
        super();
        this.lon = lon;
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    @Override
    public String toString() {
        return "lat: " + lat + ", lon: " + lon;
    }

}
