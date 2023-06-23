package com.fiap.gregory.smarthome.app.useful;

public class StringUseful {
    public static boolean isNullOrEmpty(Object obj) {
        return obj == null || obj.toString().isEmpty();
    }
}
