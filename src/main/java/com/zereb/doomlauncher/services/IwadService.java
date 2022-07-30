package com.zereb.doomlauncher.services;

import com.zereb.doomlauncher.DoomLauncher;
import com.zereb.doomlauncher.models.Iwad;
import javafx.stage.FileChooser;

import java.io.File;
import java.nio.file.Path;

public class IwadService {

    public static final FileChooser.ExtensionFilter EXTENSION_FILTER_WAD = new FileChooser.ExtensionFilter("Wad files", "*.wad", "*.WAD");
    private static IwadService instance;

    public final Iwad currentIwad = new Iwad();
    private final ConfigService configService;

    private IwadService() {
        configService = ConfigService.getInstance();
        if (configService.config.lastIwad != null) {
            currentIwad.set(Path.of(configService.config.lastIwad));
        }
    }

    public static IwadService getInstance() {
        if (instance == null)
            instance = new IwadService();
        return instance;
    }

    public void setCurrentIwad() {
        FileChooser fileChooser = new FileChooser();
        if (!currentIwad.isEmpty()) {
            fileChooser.setInitialDirectory(currentIwad.getParent().toFile());
        }

        fileChooser.setTitle("Open Iwad");
        fileChooser.getExtensionFilters().add(EXTENSION_FILTER_WAD);
        File selectedFile = fileChooser.showOpenDialog(DoomLauncher.getPrimaryStage());

        if (selectedFile != null) {
            currentIwad.set(selectedFile.toPath());
            configService.config.lastIwad = currentIwad.toString();
            configService.saveConfig();
            UiLogger.println("Set iwad: " + currentIwad);
        }
    }
}

