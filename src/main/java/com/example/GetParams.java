package com.example;

import java.util.ArrayList;

class GetParams {

    public ArrayList<String> parseQueryString(String query) {

        ArrayList<String> tempArray = new ArrayList<String>();

        for (String entry : query.split("&")) {
            String[] parts = entry.split("=");
            String value;

            if (parts.length == 1) {
                value = null;
            } else {
                value = parts[1];
            }

            tempArray.add(value);
        }
        return tempArray;
    }
}
