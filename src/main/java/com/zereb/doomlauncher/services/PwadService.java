package com.zereb.doomlauncher.services;

import com.zereb.doomlauncher.DoomLauncher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PwadService {

    public static final FileChooser.ExtensionFilter EXTENSION_FILTER_PWAD = new FileChooser.ExtensionFilter("Wad files",
        "*.wad", "*.pk3", "*.zip", "*.ini",
                   "*.WAD", "*.PK3", "*.ZIP", "*.INI"
    );
    private static PwadService instance;

    public final ObservableList<Path> pwads = FXCollections.observableArrayList();
    public final ConfigService configService;

    private PwadService() {
        configService = ConfigService.getInstance();
    }

    public static PwadService getInstance() {
        if (instance == null)
            instance = new PwadService();
        return instance;
    }

    public void add() {
        var newPwads = chooseFile()
            .stream()
            .map(File::toPath)
            .collect(Collectors.toList());

        pwads.addAll(newPwads);

    }

    public boolean moveWadToStart(int index) {
        if (pwads.size() < 2) return false;

        Collections.swap(pwads, index, 0);
        return true;
    }

    public boolean moveWadToEnd(int index) {
        if (pwads.size() < 2) return false;

        Collections.swap(pwads, index, pwads.size() - 1);
        return true;
    }


    public void moveWadUp(int index) {
        boolean notOutOfBounds = pwads.size() > index;
        boolean notFirst = index > 0;

        if (notOutOfBounds && notFirst) {
            Collections.swap(pwads, index, index - 1);
        }
    }

    public void moveWadDown(int index) {
        boolean notLast = pwads.size() - 1 > index;

        if (notLast) {
            Collections.swap(pwads, index, index + 1);
        }
    }

    public void remove(int id) {
        pwads.remove(id);
    }

    public void removeAll() {
        pwads.clear();
    }

    public Path getPwadFile(int index) {
        return pwads.get(index);
    }


    public void setPwads(List<String> strings) {
        pwads.clear();

        strings.stream()
            .map(Path::of)
            .filter(Files::exists)
            .forEach(pwads::add);
    }

    private List<File> chooseFile() {
        FileChooser fileChooser = new FileChooser();

        if (configService.config.lastPwad != null) {
            fileChooser.setInitialDirectory(Path.of(configService.config.lastPwad).toFile());
        }

        fileChooser.setTitle("Add mods");
        fileChooser.getExtensionFilters().add(EXTENSION_FILTER_PWAD);
        var result =  fileChooser.showOpenMultipleDialog(DoomLauncher.getPrimaryStage());

        if (result != null) {
            configService.config.lastPwad = result.get(result.size() - 1).toPath().getParent().toAbsolutePath().toString();
            configService.saveConfig();
        }

        return result;

    }

}

