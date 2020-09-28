package com.platunov.bannerviewer.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class ProjectUtils {

    public static Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static float tryParseFloat(String value) {
        return Float.parseFloat(value.replace(",", "."));
    }
}