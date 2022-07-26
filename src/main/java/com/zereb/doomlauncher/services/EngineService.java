package com.zereb.doomlauncher.services;

import com.zereb.doomlauncher.DoomLauncher;
import com.zereb.doomlauncher.models.Engine;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;

import java.io.File;
import java.nio.file.Path;
import java.util.Optional;

public class EngineService {

    public static final FileChooser.ExtensionFilter EXTENSION_FILTER_ALL= new FileChooser.ExtensionFilter("All Files", "**");
    private static EngineService instance;

    private final Engine current;

    private EngineService() {
        current = new Engine();
    }

    public static EngineService getInstance() {
        if (instance == null)
            instance = new EngineService();
        return instance;
    }

    public void chooseCurrentEngine() {
        chooseFile()
            .map(File::toPath)
            .ifPresent(current::set);
    }

    public void changeCurrentExec(String newAbsolutePath) {
        current.set(Path.of(newAbsolutePath));
    }

    public Engine getCurrent() {
        return current;
    }

    private Optional<File> chooseFile() {
        FileChooser fileChooser = new FileChooser();
        if (!current.isEmpty()) {
            fileChooser.setInitialDirectory(current.getParent().toFile());
        }

        fileChooser.setTitle("Open Engine");
        fileChooser.getExtensionFilters().add(EXTENSION_FILTER_ALL);
        File selectedFile = fileChooser.showOpenDialog(DoomLauncher.getPrimaryStage());

        return Optional.ofNullable(selectedFile);
    }

}

