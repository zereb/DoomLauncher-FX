package com.zereb.doomlauncher.models;

import com.zereb.doomlauncher.services.ConfigService;
import javafx.beans.property.SimpleStringProperty;

import java.nio.file.Path;

public class Engine {

    private Path path;
    public final SimpleStringProperty engineName;
    public final SimpleStringProperty engineAbsolutePath;

    public Engine() {
        engineAbsolutePath = new SimpleStringProperty();
        engineName = new SimpleStringProperty();
        engineAbsolutePath.addListener((observable, oldValue, newValue) -> set(Path.of(newValue)));

        ConfigService configService = ConfigService.getInstance();
        if (configService.config.lastEngine != null) {
            set(Path.of(configService.config.lastEngine));
        }
    }

    public void set(Path newEngine){
        if (newEngine.toFile().isDirectory()) return;

        path = newEngine;
        engineName.set(newEngine.getFileName().toString());
        engineAbsolutePath.set(newEngine.toAbsolutePath().toString());
    }

    public Path getParent() {
        return path.getParent();
    }

    public boolean isEmpty(){
        return path == null;
    }

    @Override
    public String toString() {
        if (path == null) return "";
        return path.toString();
    }
}
