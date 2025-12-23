package com.sss.demo3.util;

import java.util.regex.Pattern;

public class ValidationUtils {

    // Simple regex for phone (11 digits)
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{11}$");
    
    // Barcode: alphanumeric, 1-20 chars
    private static final Pattern BARCODE_PATTERN = Pattern.compile("^[a-zA-Z0-9]{1,20}$");

    public static boolean isValidPhone(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone).matches();
    }

    public static boolean isValidBarcode(String barcode) {
        return barcode != null && BARCODE_PATTERN.matcher(barcode).matches();
    }

    public static boolean isNotEmpty(String str) {
        return str != null && !str.trim().isEmpty();
    }
    
    public static boolean isPositiveInt(String str) {
        try {
            int val = Integer.parseInt(str);
            return val > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public static boolean isNonNegativeDouble(String str) {
        try {
            double val = Double.parseDouble(str);
            return val >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public static String escapeHtml(String s) {
        if (s == null) return null;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '<': sb.append("&lt;"); break;
                case '>': sb.append("&gt;"); break;
                case '&': sb.append("&amp;"); break;
                case '"': sb.append("&quot;"); break;
                case '\'': sb.append("&#39;"); break;
                default: sb.append(c);
            }
        }
        return sb.toString();
    }

    public static int parseInt(String str, int defaultValue) {
        if (str == null || str.trim().isEmpty()) return defaultValue;
        try {
            return Integer.parseInt(str.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static long parseLong(String str, long defaultValue) {
        if (str == null || str.trim().isEmpty()) return defaultValue;
        try {
            return Long.parseLong(str.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}