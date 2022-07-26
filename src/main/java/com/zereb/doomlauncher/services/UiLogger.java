package com.zereb.doomlauncher.services;

import javafx.beans.property.SimpleStringProperty;

public class UiLogger {

    private static UiLogger instance;
    public final SimpleStringProperty logs = new SimpleStringProperty("");

    private UiLogger() {
    }

    public static UiLogger getInstance() {
        if (instance == null)
            instance = new UiLogger();
        return instance;
    }

    public static void println(Object o) {
        println(o.toString());
    }

    public static void println(String s) {
        System.out.println(s);
        if (instance != null) {
           instance.logs.setValue(instance.logs.getValue() + s + "\n");
        }
    }
}
