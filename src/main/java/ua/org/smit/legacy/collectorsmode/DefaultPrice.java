package ua.org.smit.legacy.collectorsmode;

import java.io.File;
import ua.org.smit.common.filesystem.TxtFile;

public class DefaultPrice {

    public static final int DEFAULT_PRICE_SEC = 60 * 15; // 15 min

    private final String absolutePath;

    DefaultPrice(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    long getValue() {
        TxtFile txt = new TxtFile(absolutePath + File.separator + "default_price_sec.txt");
        if (!txt.exists()) {
            txt.add(DEFAULT_PRICE_SEC);
        }
        return txt.readFirstInteger();
    }

}
