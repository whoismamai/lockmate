package com.telenet.lockmate.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppUtil {

    public static final String APP_NAME = "LockMate";
    public static final String APP_VERSION = "1.0.0";
    public static final String SUPPORT_EMAIL = "jackmamaiw@gmail.com";

    // Static logger
    public static final Logger LOG = LoggerFactory.getLogger(AppUtil.class);

    public static String getAppInfo() {
        return APP_NAME + " v" + APP_VERSION + " - Support: " + SUPPORT_EMAIL;
    }

}
