package instamart.api.responses.shopper;

import instamart.api.objects.shopper.Prereplacement;
import instamart.api.responses.BaseResponseObject;

public class PrereplacementsResponse extends BaseResponseObject {

    private Prereplacement prereplacement;

    public Prereplacement getPrereplacement() {
        return prereplacement;
    }

    public void setPrereplacement(Prereplacement prereplacement) {
        this.prereplacement = prereplacement;
    }

}
