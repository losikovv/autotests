package instamart.api.helpers;

import java.util.HashMap;

abstract class ApiHelperBase {
    /**
     * Определяем, есть ли в хэшэ указанная пара из инта и стринга
     */
    public boolean hashMapContainsIntAndString(int integer, String string, HashMap<Integer, String> hashMap) {
        if (hashMap.containsKey(integer)) return hashMap.get(integer).contains(string);
        return false;
    }
}
