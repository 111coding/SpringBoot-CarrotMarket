package com.example.carrotmarket.utils;


public class AddressUtils {
    public static String getDisplayName(String fullName){
        String[] split = fullName.split(" ");
        int length = split.length;
        return length > 1 ? split[length-1]:fullName;
    }
}
