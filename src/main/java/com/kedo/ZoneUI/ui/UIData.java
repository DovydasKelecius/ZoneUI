package com.kedo.ZoneUI.ui;

import java.util.HashMap;
import java.util.Map;

public class UIData {

    private final Map<String, Object> data = new HashMap<>();

    public UIData() {
    }

    public UIData(String key, Object value) {
        this.put(key, value);
    }

    public UIData put(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public String getString(String key) {
        return (String) data.get(key);
    }

    public String getString(String key, String defaultValue) {
        return (String) data.getOrDefault(key, defaultValue);
    }

    public Integer getInt(String key) {
        return (Integer) data.get(key);
    }

    public Integer getInt(String key, Integer defaultValue) {
        return (Integer) data.getOrDefault(key, defaultValue);
    }

    public Boolean getBoolean(String key) {
        return (Boolean) data.get(key);
    }

    public Boolean getBoolean(String key, Boolean defaultValue) {
        return (Boolean) data.getOrDefault(key, defaultValue);
    }
}
