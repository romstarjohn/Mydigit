package com.project.android.finanzm.database;

import java.util.HashMap;
import java.util.Map;

public enum Type{
    CATEGORIES(1), PRODUCT(2);

    private int value;
    private static Map map = new HashMap<>();

    private Type(int value) {
        this.value = value;
    }

    static {
        for ( Type pageType : Type.values()) {
            map.put(pageType.value, pageType);
        }
    }

    public static  Type valueOf(int pageType) {
        return ( Type) map.get(pageType);
    }

    public int getValue() {
        return value;
    }

}
