package com.fiap.gregory.smarthome.domain.useful;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StringUseful {
    public static boolean isNullOrEmpty(Object obj) {
        return obj == null || obj.toString().isEmpty();
    }

    public static Date convertToDate(String dateString) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        return format.parse(dateString);
    }

    public static Integer convertToInt(String str) {
        return Integer.parseInt(str);
    }
}
