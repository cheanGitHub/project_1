package com.cc.jackson;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "category", visible = true)
public class AppContent {

    private String category;
    private String expId;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getExpId() {
        return expId;
    }

    public void setExpId(String expId) {
        this.expId = expId;
    }

    @Override
    public String toString() {
        return "AppContent{" +
                "category='" + category + '\'' +
                ", expId='" + expId + '\'' +
                '}';
    }
}
