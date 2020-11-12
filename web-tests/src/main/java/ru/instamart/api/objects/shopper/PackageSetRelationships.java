package instamart.api.objects.shopper;

import instamart.api.objects.BaseObject;

public class PackageSetRelationships extends BaseObject {
    private Assembly assembly;

    public Assembly getAssembly() {
        return assembly;
    }

    public void setAssembly(Assembly assembly) {
        this.assembly = assembly;
    }

}
