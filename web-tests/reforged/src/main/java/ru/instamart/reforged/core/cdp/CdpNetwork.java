package ru.instamart.reforged.core.cdp;

import org.openqa.selenium.devtools.v107.network.Network;
import ru.instamart.reforged.core.Kraken;

public final class CdpNetwork {

    public static void disableNetworkTacking(){
        Kraken.getDevTools().send(Network.disable());
    }
}
