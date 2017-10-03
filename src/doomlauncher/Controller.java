/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doomlauncher;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
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
    public TextArea taOut;
    
    public Files files;
    ProcessBuilderD processBuilderD;

    
    
    public void addEngine() {
        File selectedFile = getFile(EF_ALL);
        if (selectedFile != null) {
            files.addEngine(selectedFile);
            refresh();
        }
    }
    
    public void addIwad() {
        File selectedFile = getFile(EF_WAD);
        if (selectedFile != null) {
            files.addIwad(selectedFile);
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
            files.setEngine(selectedFile, cbEngine.getSelectionModel().getSelectedIndex());
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
    
    
    public void launch(){
        List<String> cmd_list= new ArrayList<String>();
        cmd_list.add(files.getEngine(cbEngine.getSelectionModel().getSelectedIndex()).getPath());
        cmd_list.add("-iwad");
        cmd_list.add(files.getIwad(cbIwad.getSelectionModel().getSelectedIndex()).getPath());
        
        for(DLFile pwad: files.getPwads()){
            if(pwad.getName().toLowerCase().contains(".ini")){
                 cmd_list.add("-config");
                 cmd_list.add(pwad.getPath());
            }
            else if(pwad.getName().toLowerCase().contains(".pk3") || pwad.getName().toLowerCase().contains(".zip") || pwad.getName().toLowerCase().contains(".wad")){
                cmd_list.add("-file");
                cmd_list.add(pwad.getPath());
            }
            else{files.removePwad(files.getPwads().indexOf(pwad)); }
        }
 
        String[] cmd = cmd_list.toArray(new String[cmd_list.size()]);
        for (String string : cmd) {
           Printer.print(string);
        }
        processBuilderD = new ProcessBuilderD(cmd);
        processBuilderD.addObserver(this);
    }
    
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        files=new Files();
        Printer.printer.addObserver(this);
     //   taOut.setText(Printer.getLogStr());
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
        Stage fileChoose = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(files.dlConfig.getConfigValue(CFG_PR_DEFAULT_FOLDER)));
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(ef);
        return  fileChooser.showOpenDialog(fileChoose);
    }
    
    private List<File> getFile(ExtensionFilter ef, boolean  OWERRIDE){
        Stage fileChoose = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(files.dlConfig.getConfigValue(CFG_PR_DEFAULT_FOLDER)));
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(ef);
        return  fileChooser.showOpenMultipleDialog(fileChoose);
    }

    @Override
    public void update(Observable o, Object arg) {
        taOut.appendText(Printer.getLogStr());
    }
    
}
