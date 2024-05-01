package com.smbc.epix.transaction.services.utils;

import org.springframework.util.StringUtils;

public class Util {

    public static String convertBooleanToYesOrNo(String booleanAsString) {
        String requiredInput = "No";
        if (!StringUtils.isEmpty(booleanAsString)) {
            requiredInput = "No";
            if ("True".equalsIgnoreCase(booleanAsString)) {
                requiredInput = "Yes";
            }
        }
        return requiredInput;
    }

    public static String getString(String string) {
        if (StringUtils.isEmpty(string)) {
            return "";
        }
        return string;
    }

}
