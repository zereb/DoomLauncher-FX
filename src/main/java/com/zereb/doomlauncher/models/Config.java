package com.zereb.doomlauncher.models;

public class Config {

    public String lastIwad;
    public String lastPwad;
    public String lastEngine;

    public Config() {}

    @Override
    public String toString() {
        return "Config{" +
            "lastIwad='" + lastIwad + '\'' +
            ", lastPwad='" + lastPwad + '\'' +
            ", lastEngine='" + lastEngine + '\'' +
            '}';
    }
}
