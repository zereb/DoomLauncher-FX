/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doomlauncher;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class Controller implements Initializable, Constants, Observer{
    
    @FXML
    public Button btnLaunch,
                   btnAddEngien;
    @FXML
    public ComboBox cbEngine,
                     cbIwad;
    @FXML
    public ListView<String> lwPwad;
    @FXML
    public TextArea taOut,taCustomParameters;
    
    public Files files;
    ProcessBuilderD processBuilderD;

    
    
    //GLOBAL MENU
    public void editConfig() {
        if (Desktop.isDesktopSupported()) {
            new Thread(() -> {
                try {
                    Desktop.getDesktop().open(files.dlConfig.getConfigPath().toFile());
                } catch (IOException e) {
                    Printer.print(e.toString());
                }
            }).start();
        }
    }
    
    public void importConfig(){
        File selectedFile = getFile(EF_CFG);
        if (selectedFile != null) {
            files=new Files(selectedFile.getAbsolutePath().replaceAll(".cfg", ""));
            refresh();
        }
    }
    public void exportConfig(){
        File selectedFile = saveFile(EF_CFG);
        if (selectedFile != null) {
           files.dlConfig.saveConfig(selectedFile.getAbsolutePath());
            refresh();
        }
    }
    
    public void about(){
        Printer.print("info: https://github.com/zereb/DoomLauncherFX");
    }
    
    //top buttons
    
    public void addEngine() {
        File selectedFile = getFile(EF_ALL);
        if (selectedFile != null) {
            files.addEngine(selectedFile);
            refresh();
        }
    }
    

    public void changeEngine(){
        File selectedFile = getFile(EF_ALL);
        if (selectedFile != null) {
            files.setEngine(selectedFile, cbEngine.getSelectionModel().getSelectedIndex());
            refresh();
        }
    }
    
    public void changeIwad(){
        File selectedFile = getFile(EF_WAD);
        if (selectedFile != null) {
            files.setIwad(selectedFile);
            refresh();
        }
    }
    
    public void  addPwad(){
       List<File> selectedFile = getFile(EF_PWAD, true);
        if (selectedFile != null) {
            files.addPwad(selectedFile);
            refresh();
        }
    }
    
    public void removePwad(){
        files.removePwad(lwPwad.getSelectionModel().getSelectedIndex());
        refresh();
    }
    
    public void removeAllPwad(){
        files.removeAllPwad();
        refresh();
    }
    
    public void movePwadDown(){
        try {
            files.movePwadDown(lwPwad.getSelectionModel().getSelectedIndex());
        } catch (IndexOutOfBoundsException e) {
            Printer.print(e.toString());
        }
        
        refresh();
    }
    public void movePwadUp(){
        try {
            files.movePwadUp(lwPwad.getSelectionModel().getSelectedIndex());
        } catch (IndexOutOfBoundsException e) {
            Printer.print(e.toString());
        }
        refresh();
    }
    
    
    public void showCommandLine(){
        for (String cmd : getCMD()) {
            Printer.print(cmd);
        }
    }
    
    
    public void launch(){
        processBuilderD = new ProcessBuilderD(getCMD());
        processBuilderD.addObserver(this);
    }

    public String[] getCMD() {
        List<String> cmd_list = new ArrayList<String>();
        cmd_list.add(files.getEngine(cbEngine.getSelectionModel().getSelectedIndex()).getPath());
        cmd_list.add("-iwad");
        try {
            cmd_list.add(files.getIwad(cbIwad.getSelectionModel().getSelectedIndex()).getPath());
        } catch (IndexOutOfBoundsException e) {
            Printer.print(e.toString());
        }

        for (DLFile pwad : files.getPwads()) {
            if (pwad.getName().toLowerCase().contains(".ini")) {
                cmd_list.add("-config");
                cmd_list.add(pwad.getPath());
            } else if (pwad.getName().toLowerCase().contains(".pk3") || pwad.getName().toLowerCase().contains(".zip") || pwad.getName().toLowerCase().contains(".wad")) {
                cmd_list.add("-file");
                cmd_list.add(pwad.getPath());
            } else {
                files.removePwad(files.getPwads().indexOf(pwad));
            }
        }
        for (String parameter : taCustomParameters.getText().split(" ")) {
            cmd_list.add(parameter);
        }
        String[] cmd = cmd_list.toArray(new String[cmd_list.size()]);
        return  cmd;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        files=new Files(CONFIG_NAME);
        Printer.printer.addObserver(this);
        refresh();
    }
    
    private void refresh(){
        cbEngine.getItems().clear();
        cbIwad.getItems().clear();
        lwPwad.getItems().clear();
        for (DLFile engine : files.getEngines()) {
            cbEngine.getItems().add(engine.getName());
        }
        for (DLFile iwad : files.getIwads()) {
            cbIwad.getItems().add(iwad.getName());
        }
        for(DLFile pwad : files.getPwads()){
            lwPwad.getItems().add(pwad.getPath());
        }
        cbEngine.getSelectionModel().selectFirst();
        cbIwad.getSelectionModel().selectFirst();
    }
    
    
    private File getFile(ExtensionFilter ef){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(files.dlConfig.getConfigValue(CFG_PR_DEFAULT_FOLDER)));
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(ef);
        return  fileChooser.showOpenDialog(DoomLauncher.getPrimaryStage());
    }
    private File saveFile(ExtensionFilter ef){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(files.dlConfig.getConfigValue(CFG_PR_DEFAULT_FOLDER)));
        fileChooser.setTitle("Save Resource File");
        fileChooser.getExtensionFilters().addAll(ef);
        return  fileChooser.showSaveDialog(DoomLauncher.getPrimaryStage());
    }
    
    private List<File> getFile(ExtensionFilter ef, boolean  OVERRIDE){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(files.dlConfig.getConfigValue(CFG_PR_DEFAULT_FOLDER)));
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(ef);
        return  fileChooser.showOpenMultipleDialog(DoomLauncher.getPrimaryStage());
    }

    @Override
    public void update(Observable o, Object arg) {
        taOut.appendText(Printer.getLogStr());
    }
    
}
