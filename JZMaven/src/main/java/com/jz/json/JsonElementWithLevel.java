package com.jz.json;

import com.google.gson.*;

class JsonElementWithLevel {
    private JsonElement jsonElement;
    private String level;

    JsonElementWithLevel(JsonElement jsonElement, String level) {
        this.jsonElement = jsonElement;
        this.level = level;
    }

    public JsonElement getJsonElement() {
        return this.jsonElement;
    }

    public String getLevel() {
        return this.level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public static void main(String[] args) {
        String a = "$.listing.seller.ebayStore";
        String currentLevel = "$.dateValue.value";
        if(a.startsWith("$")) {
            currentLevel = a + currentLevel.substring(1);
        }

        System.out.print(currentLevel);
    }

}
