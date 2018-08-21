package com.sociallogin.constants;

/**
 * Created by Santosh on 16-08-2018.
 */
public class AppConstants {

    public enum SharedPreferenceKeys {
        USER_NAME("userName"),
        USER_EMAIL("userEmail"),
        USER_IMAGE_URL("userImageUrl");


        private String value;

        SharedPreferenceKeys(String value) {
            this.value = value;
        }

        public String getKey() {
            return value;
        }
    }


    public static final String GOOGLE_CLIENT_ID = "81345932573-u1ra144tcjts321i7k9t6tdffuhjk4tm.apps.googleusercontent.com";

    public static final String TWITTER_KEY = "FwFPnIRYBouYLvgpqJQRlXiKr";
    public static final String TWITTER_SECRET = "ZMRhvt9evKdf2hzgFQokFU0jhjRtpWk4bCSpgegxYO2iHPIpWr";


}
