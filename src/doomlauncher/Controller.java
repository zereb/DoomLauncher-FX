/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doomlauncher;

import static doomlauncher.DoomLauncher.dl;
import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;


public class Controller implements Initializable, Constants{
    
    @FXML
    private Button btnLaunch,
                   btnAddEngien;
    @FXML
    private ComboBox cbEngine,
                     cbIwad;
    
   public List<String> enginesList;
    
    
    public void addEngine() {
        Stage fileChoose = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("All Files", "**"));
        File selectedFile = fileChooser.showOpenDialog(fileChoose);
        if (selectedFile != null) {
            dl.doomLauncherJConfig.setConfigValue(CONFIG_PROPERTY_ENGINE,dl.doomLauncherJConfig.getConfigValue(CONFIG_PROPERTY_ENGINE)+","+selectedFile.getAbsolutePath());
            cbEngine.getItems().add(dl.doomLauncherJConfig.getConfigValue(CONFIG_PROPERTY_CURR_ENGINE));
        }
    }
    
    public void launch(){
     //   ProcessBuilderD processBuilderD = new ProcessBuilderD(s);
    }
    
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       enginesList=Arrays.asList(dl.doomLauncherJConfig.getConfigValue(CONFIG_PROPERTY_ENGINE).split(","));
       
       cbEngine.getItems().addAll(enginesList);
       cbIwad.getItems().add(dl.doomLauncherJConfig.getConfigValue(CONFIG_PROPERTY_IWAD_PATH));
    }
    
    
    
}
