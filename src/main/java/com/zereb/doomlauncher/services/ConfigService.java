package com.zereb.doomlauncher.services;

import com.google.gson.Gson;
import com.zereb.doomlauncher.DoomLauncher;
import com.zereb.doomlauncher.models.Config;
import com.zereb.doomlauncher.models.Preset;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

public class ConfigService {

    public static ConfigService instance;
    public static final String XDG_CONFIG_HOME = System.getenv().get("XDG_CONFIG_HOME") == null ? "" : System.getenv().get("XDG_CONFIG_HOME");

    public static final String CONFIG_PATH = XDG_CONFIG_HOME + "/DoomLauncherFx/config";
    public static final String CONFIG_FOLDER = XDG_CONFIG_HOME + "/DoomLauncherFx/";

    public static final FileChooser.ExtensionFilter EXTENSION_FILTER_PRESET = new FileChooser.ExtensionFilter("Preset files", "*.preset");

    public Config config;
    private final Gson gson;

    public static ConfigService getInstance() {
        if (instance == null)
            instance = new ConfigService();
        return instance;
    }

    private ConfigService() {
        config = new Config();
        gson = new Gson();
        initConfigFolder();
        loadConfig();
    }

    public void loadConfig() {
        try {
            String json = new String(Files.readAllBytes(Path.of(CONFIG_PATH).toAbsolutePath()));
            config = gson.fromJson(json, Config.class);
            UiLogger.println("loaded config: " + config);
        } catch (IOException e) {
            UiLogger.println("Can't load config: " + e);
        }
    }

    public void saveConfig(){
        try {
            String json = gson.toJson(config);
            Files.writeString(Path.of(CONFIG_PATH), json, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            UiLogger.println("Saved config: \n" + json);
        } catch (IOException e) {
            UiLogger.println("Can't save config: " + e);
        }
    }

    public Optional<Preset> loadPreset() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(Path.of(CONFIG_FOLDER).toFile());

        fileChooser.setTitle("Load preset");
        fileChooser.getExtensionFilters().add(EXTENSION_FILTER_PRESET);
        File selectedFile = fileChooser.showOpenDialog(DoomLauncher.getPrimaryStage());

        if (selectedFile != null) {
            try {
                String json = new String(Files.readAllBytes(selectedFile.toPath()));
                Preset preset = gson.fromJson(json, Preset.class);
                UiLogger.println("Loaded preset: " + json);

                return Optional.ofNullable(preset);
            } catch (IOException e) {
                UiLogger.println("Can't load preset: " + e);
            }
        }

        return Optional.empty();
    }

    public void savePreset(Preset preset) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(Path.of(CONFIG_FOLDER).toFile());

        fileChooser.setTitle("Save preset");
        fileChooser.getExtensionFilters().add(EXTENSION_FILTER_PRESET);
        File selectedFile = fileChooser.showSaveDialog(DoomLauncher.getPrimaryStage());

        if (selectedFile != null) {
            try {
                String json = gson.toJson(preset);
                String extension = selectedFile.getName().endsWith(".preset") ? "" : ".preset";
                Path fileToSave = Path.of(selectedFile.getAbsolutePath() + extension);
                Files.writeString(fileToSave, json, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                UiLogger.println("Saved preset: " + json);
            } catch (IOException e) {
                UiLogger.println("Can't save preset: " + e);
            }
        }
    }

    private void initConfigFolder() {
        try {
            Path configFolder = Path.of(CONFIG_FOLDER).toAbsolutePath();
            if (!Files.exists(configFolder)) {
                Files.createDirectories(configFolder);
                UiLogger.println("Created replays directory: " + configFolder);
            }
        } catch (IOException e) {
            UiLogger.println("Can't create replay directory: " + e);
        }
    }



}
