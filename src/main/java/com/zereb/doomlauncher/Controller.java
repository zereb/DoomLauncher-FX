/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zereb.doomlauncher;

import com.zereb.doomlauncher.models.Preset;
import com.zereb.doomlauncher.services.*;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.*;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Controller implements Initializable{

    public TextField engineTextField;
    public Label iwadLabel;
    public ListView<Path> pwadListView;
    public TextArea outputTextArea;
    public TextArea customParamsTextArea;

    private final DoomProcessBuilder processBuilder;
    private final IwadService iwadService;
    private final EngineService engineService;
    private final PwadService pwadService;
    private final UiLogger logger;
    private final ConfigService configService;

    public Controller() {
        logger = UiLogger.getInstance();
        iwadService = IwadService.getInstance();
        engineService = EngineService.getInstance();
        pwadService = PwadService.getInstance();
        processBuilder = DoomProcessBuilder.getInstance();
        configService = ConfigService.getInstance();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        iwadLabel.textProperty().bind(iwadService.currentIwad.iwadName);
        engineTextField.textProperty().bindBidirectional(engineService.getCurrent().engineAbsolutePath);
        pwadListView.setItems(pwadService.pwads);
        outputTextArea.textProperty().bind(logger.logs);
    }

    public void changeEngine(){
        engineService.chooseCurrentEngine();
    }
    
    public void changeIwad(){
        iwadService.setCurrentIwad();
    }
    
    public void  addPwad(){
        pwadService.add();
    }
    
    public void removePwad(){
        pwadService.remove(pwadListView.getSelectionModel().getSelectedIndex());
    }
    
    public void removeAllPwad(){
        pwadService.removeAll();
    }
    
    public void movePwadDown(){
        int index = pwadListView.getSelectionModel().getSelectedIndex();
        pwadService.moveWadDown(index);
        pwadListView.getSelectionModel().selectNext();
    }
    public void movePwadUp(){
        int index = pwadListView.getSelectionModel().getSelectedIndex();
        pwadService.moveWadUp(index);
        pwadListView.getSelectionModel().selectPrevious();
    }
    
    public void openInFolder(){
        int index = pwadListView.getSelectionModel().getSelectedIndex();
        if (index > 0)
            openFile(pwadService.getPwadFile(index).toFile());
    }

    public void showCommandLine(){
        StringBuilder builder = new StringBuilder();
        for (String cmd : getCMD()) {
            builder.append(cmd).append(" ");
        }
        UiLogger.println(builder.toString());
    }

    public void savePreset() {
        Preset preset = new Preset();
        preset.iwad = iwadService.currentIwad.toString();
        preset.engine = engineService.getCurrent().toString();
        preset.customCommands = customParamsTextArea.getText();
        preset.pwads = pwadService.pwads.stream()
            .map(Path::toString)
            .collect(Collectors.toList());

        configService.savePreset(preset);
    }

    public void loadPreset() {
        Optional<Preset> optional = configService.loadPreset();
        optional.ifPresent(preset -> {
            pwadService.setPwads(preset.pwads);
            iwadService.currentIwad.set(Path.of(preset.iwad));
            engineService.changeCurrentExec(preset.engine);
            customParamsTextArea.setText(preset.customCommands);
        });
    }

    public void launch(){
        configService.config.lastEngine = engineService.getCurrent().toString();
        configService.saveConfig();
        processBuilder.startDoom(getCMD());
    }

    public List<String> getCMD() {
        List<String> commands = new ArrayList<>();

        commands.add(engineService.getCurrent().toString());
        commands.add("-iwad");
        commands.add(iwadService.currentIwad.toString());
        commands.addAll(Arrays.asList(customParamsTextArea.getText().split(" ")));

        for (Path pwad : pwadService.pwads) {
            var pwadName = pwad.getFileName().toString();
            if (pwadName.toLowerCase().contains(".ini")) {
                commands.add("-config");
                commands.add(pwad.toAbsolutePath().toString());
            } else if (Stream.of(".pk3", ".zip", ".wad").anyMatch(pwadName.toLowerCase()::contains)) {
                commands.add("-file");
                commands.add(pwad.toAbsolutePath().toString());
            }
        }

        return commands;
    }

    public void openFile(File f) {
        if (Desktop.isDesktopSupported()) {
            Executors.newSingleThreadExecutor().execute(() -> safeOpenFile(f));
        }
    }

    private void safeOpenFile(File f) {
        try {
            Desktop.getDesktop().open(f);
        } catch (IOException e) {
            UiLogger.println(e.toString());
        }
    }

    public void setEngineExecutable() {
        engineService.changeCurrentExec(engineTextField.getText());
    }
}
