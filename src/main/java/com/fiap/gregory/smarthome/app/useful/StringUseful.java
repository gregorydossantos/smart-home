package com.fiap.gregory.smarthome.app.useful;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StringUseful {
    public static boolean isNullOrEmpty(Object obj) {
        return obj == null || obj.toString().isEmpty();
    }

    public static Date convertToDate(String dateString) throws ParseException {
        Date date;
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        return date = format.parse(dateString);
    }

    public static Integer convertToInt(String str) {
        return Integer.parseInt(str);
    }
}
