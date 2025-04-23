// File: com.example.hafleti.Utils.ImageUtils.java
package com.example.hafleti.Utils;

import android.util.Base64;

public class ImageUtils {

    public static byte[] decodeBase64Image(String base64) {
        if (base64 == null || base64.isEmpty()) return null;
        if (base64.contains(",")) {
            base64 = base64.split(",")[1];
        }
        return Base64.decode(base64, Base64.DEFAULT);
    }
}
